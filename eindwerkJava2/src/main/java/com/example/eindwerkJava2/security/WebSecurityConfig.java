package com.example.eindwerkJava2.security;

import com.example.eindwerkJava2.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Spring security configuration for setting up the necessary beans: userDetailsService, passwordEncoder, authenticationProvider, accessDeniedHandler.
 * Also to configure the authenticationProvider, loginPage, endpoint url authorities.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Wrapping user service class that is recognized by Spring security.
     *
     * @return The UserDetailsServiceImpl().
     * @see com.example.eindwerkJava2.service.UserDetailsServiceImpl
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Encoder for storing the user's password encrypted in the database.
     *
     * @return BCryptPasswordEncoder as the used encoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The authenticationProvider that is used with Spring Security.
     *
     * @return authProvider object which uses the userDetailsService and the BcryptPasswordEncoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provider for the authentication to be used with Spring Security.
     *
     * @param auth Is used for setting authenticationProvider as the to be user provider within the Spring Security framework.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Defines the login page, accessDeniedHandler as well as the needed authorities for specific endpoints;
     *
     * @param http Uses the HttpSecurity class for defining the above.
     * @throws Exception An exception can be thrown in case an issue arises within the method.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
                .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
                .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
                .antMatchers("/delete/**").hasAuthority("ADMIN")
                .antMatchers("/users/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/api/v1/articles")
                .antMatchers("/api/v1/sale-headers");
    }

    /**
     * This bean is used for setting up the CustomAccesDeniedHandler.
     *
     * @return The CustomAccessDeniedHandler.
     * @see com.example.eindwerkJava2.security.CustomAccessDeniedHandler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
