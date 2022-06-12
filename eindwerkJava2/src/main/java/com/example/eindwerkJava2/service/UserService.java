package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.model.dto.UserDto;
import com.example.eindwerkJava2.repositories.RoleRepository;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the service layer for users.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Service
public class UserService {
    /**
     * Autowiring of the UserRepository from the IOC container.
     */
    @Autowired
    private final UserRepository userRepository;
    /**
     * Autowiring of the RoleRepository from the IOC container.
     */
    @Autowired
    private final RoleRepository roleRepository;
    /**
     * Autorwiring of the BcryptPasswordEncoder from the IOC container.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Role DEFAULT_ROLE = new Role("USER");

    /**
     * Dependency injection of the UserRepository and RoleRepository into the UserService.
     *
     * @param userRepository The to be injected UserRepository.
     * @param roleRepository The to be injected RoleRepository.
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Getter method for getting the active user value.
     *
     * @return The active user value is returned.
     */
    public SuccessEvaluator<User> getActiveUsers() {
        SuccessEvaluator<User> retrievedUsers = new SuccessEvaluator<>();
        List<User> activeUsers = this.userRepository.findByActiveUser(1);
        if (activeUsers.size() > 0) {
            retrievedUsers.setEntities(activeUsers);
            retrievedUsers.setIsSuccessfull(true);
        } else {
            retrievedUsers.setIsSuccessfull(false);
            retrievedUsers.setMessage("No active users found within the database.");
        }
        return retrievedUsers;
    }

    /**
     * Getter method for getting the user name.
     *
     * @param userName The to be retrieved user name.
     * @return The retrieved user object is returned.
     */
    public User getUserByUserName(String userName) {
        if (userRepository.existsUserByUserNameAndActiveUser(userName,1)) {
            User retrievedUser = userRepository.findByUserNameAndActiveUser(userName,1);
            return retrievedUser;
        } else throw new IllegalStateException("User with username: " + userName + " could not been found.");
    }

    /**
     * Method for saving or updating a user.
     *
     * @param userDto      The to be saved user.
     * @param userImage The image of the user that is saved within the user object.
     */
    public SuccessEvaluator<UserDto> saveUser(UserDto userDto, byte[] userImage) {
        SuccessEvaluator<UserDto> success = new SuccessEvaluator<UserDto>();
        boolean existsUserName = userRepository.existsUserByUserNameAndActiveUser(userDto.getUserName(),1);
        if (existsUserName) {
            User duplicateUser = userRepository.findByUserNameAndActiveUser(userDto.getUserName(),1);
            if (userDto.getUserId() == null && duplicateUser.getActiveUser() == 1) {
                success.setIsSuccessfull(false);
                success.setMessage("Cannot save this user because a user with user name " + userDto.getUserName() + " already exists!");
                return success;
            } else if (userDto.getUserId() != null && userDto.getUserId() != duplicateUser.getUserId() && duplicateUser.getActiveUser() == 1) {
                success.setIsSuccessfull(false);
                success.setMessage("Cannot modify this user because a user with user name " + userDto.getUserName() + " already exists!");
                return success;
            }
            userDto.convertUserToDTO(duplicateUser);
        }
        userImageHandler(userDto, userImage);
        SuccessEvaluator<UserDto> passwordAndRolesHandler = passwordAndRolesHandler(userDto);
        if(passwordAndRolesHandler.getIsSuccessfull()){
            User updatedUser = passwordAndRolesHandler.getEntity().convertDTOtoUser();
            userRepository.save(updatedUser);
            success.setIsSuccessfull(true);
            success.setMessage("User " + updatedUser.getUserName() + " was successfully saved!");
        } else {
            success.setIsSuccessfull(false);
            success.setMessage(passwordAndRolesHandler.getMessage());
            success.setEntity(passwordAndRolesHandler.getEntity());
        }
        return success;
    }

    private SuccessEvaluator<UserDto> passwordAndRolesHandler(UserDto userDto) {
        SuccessEvaluator<UserDto> passwordSuccess = new SuccessEvaluator<>();
        if (userDto.getPassword().isEmpty()&&userDto.getUserId()==null) {
            passwordSuccess.setMessage("Please input a password!");
            passwordSuccess.setIsSuccessfull(false);
            return passwordSuccess;
        } else {
            if (userDto.getUserId() == null) {
                userDto.addOneRole(roleRepository.findById(1).get());
                String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
                userDto.setPassword(encryptedPassword);
                passwordSuccess.setEntity(userDto);
                return passwordSuccess;
            }
            if (userRepository.existsUserByUserNameAndActiveUser(userDto.getUserName(),1)) {
                User user1 = userRepository.findByUserNameAndActiveUser(userDto.getUserName(),1);
                if (user1.getUserId() == userDto.getUserId()) {
                    userDto.setRoles(userRepository.findById(userDto.getUserId()).get().getRoles());
                }
            }
            return modifyExistingPasswordHandler(userDto);
        }
    }
    private SuccessEvaluator<UserDto> modifyExistingPasswordHandler(UserDto userDto){
        SuccessEvaluator<UserDto> passwordModificationSuccess = new SuccessEvaluator<>();
        passwordModificationSuccess.setEntity(userDto);
        if(!userDto.getNewPassword().isEmpty()&&!userDto.getConfirmPassword().isEmpty()){
            String currentPassword = userRepository.getById(userDto.getUserId()).getPassword();
            boolean doesPasswordMatch = passwordEncoder.matches(userDto.getPassword(), currentPassword);
            if(!doesPasswordMatch){
                passwordModificationSuccess.setIsSuccessfull(false);
                passwordModificationSuccess.setMessage("Your inputted current password does not match the password within the database, please try again.");
                return passwordModificationSuccess;
            }
            if(Objects.equals(userDto.getNewPassword(), userDto.getConfirmPassword())){
                if(!passwordValidator(userDto.getConfirmPassword())){
                    passwordModificationSuccess.setIsSuccessfull(false);
                    passwordModificationSuccess.setMessage("Password does not fulfill all criterias:" +
                            "\n- At least one numeric character" +
                            "\n- At least one lower case character" +
                            "\n- At least one upper case character" +
                            "\n- At least one special symbol among @#$%" +
                            "\n- Password length should be at least 8-20 characters long");
                } else {
                    String encryptedPassword = passwordEncoder.encode(userDto.getConfirmPassword());
                    userDto.setPassword(encryptedPassword);
                    passwordModificationSuccess.setEntity(userDto);
                    passwordModificationSuccess.setIsSuccessfull(true);
                    passwordModificationSuccess.setMessage("Password has been modified");
                }
            } else {
                passwordModificationSuccess.setIsSuccessfull(false);
                passwordModificationSuccess.setMessage("The new password and the confirmed password are not equal");
            }
        }
        return passwordModificationSuccess;
    }

    private static boolean passwordValidator(String password){
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$"; //Password must have:
        // one numeric char, one lowercase char, one uppercase char, one special symbol (@#$%), password length 8-20 char
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void userImageHandler(UserDto userDto, byte[] userImage) {
        boolean existsUserId = userRepository.existsUserByUserId(userDto.getUserId());
        if (userImage.length == 0) {
            if (existsUserId) {
                User currentUser = userRepository.getById(userDto.getUserId());
                userDto.setUserImage(currentUser.getUserImage());
            }
        } else {
            userDto.setUserImage(userImage);
        }
    }

    /**
     * Method to find a user from the persistence layer.
     *
     * @param id The user id for obtaining the user object.
     * @return The user object is returned.
     */
    public SuccessEvaluator<User> findById(Long id) {
        SuccessEvaluator<User> success = new SuccessEvaluator<>();
        if (userRepository.existsUserByUserId(id)) {
            User user = userRepository.findById(id).get();
            success.setEntity(user);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("User with Id "+id+" not found!");
        }
        return success;
    }

    /**
     * Method for deleting a user by setting the active user = 0.
     *
     * @param user The to be deleted user object.
     */
    public SuccessObject deleteUser(User user) {
        SuccessObject success = new SuccessEvaluator<>();
        user.setActiveUser(0);
        this.userRepository.save(user);
        if (userRepository.findById(user.getUserId()).get().getActiveUser() == 0) {
            success.setIsSuccessfull(true);
            success.setMessage("User " + user.getUserName() + " (ID: " + user.getUserId() + ")" + " was successfully removed.");
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("User " + user.getUserName() + " (ID: " + user.getUserId() + ")" + " could not be removed.");
        }
        return success;
    }

}
