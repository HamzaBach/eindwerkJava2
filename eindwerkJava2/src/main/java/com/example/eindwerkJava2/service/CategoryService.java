package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.CategoryRepository;
import com.example.eindwerkJava2.wrappers.CategorySuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategorySuccess getCategories() {
        CategorySuccess success = new CategorySuccess();
        List<Category> retrievedCategories = categoryRepository.findAllActiveCategories();
        if (retrievedCategories.isEmpty()) {
            success.setIsSuccessfull(false);
            success.setMessage("No categories where found, please add categories!");
        } else {
            success.setIsSuccessfull(true);
            success.setCategories(retrievedCategories);
        }
        return success;
    }

    public SuccessObject addCategory(Category category) {
        SuccessObject isSaveSuccessful = new CategorySuccess();
        Boolean existsCategoryByCategoryName = categoryRepository.existsCategoryByCategoryName(category.getCategoryName());
        if (existsCategoryByCategoryName) {
            Category categoryWithSameName = categoryRepository.findByCategoryName(category.getCategoryName()).get();
            // use case if a new category gets named to the name of an already present category name -> block!
            if (category.getCategoryId() == null && categoryWithSameName.getActive() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("New category cannot be added because this category name " + category.getCategoryName() + " already exists!");
                return isSaveSuccessful;
            }
            // use case if an existing article gets renamed to the name of an already present article name -> block!
            if (category.getCategoryId() != null
                    && categoryWithSameName.getCategoryId() != category.getCategoryId()
                    && categoryWithSameName.getActive() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("Cannot modify this category because the category name " + category.getCategoryName() + " already exists!");
                return isSaveSuccessful;
            }
        }
        categoryRepository.save(category);
        isSaveSuccessful.setIsSuccessfull(true);
        isSaveSuccessful.setMessage("Category " + category.getCategoryName() + " is successfully saved!");
        return isSaveSuccessful;
    }


    public CategorySuccess findById(Long id) {
        CategorySuccess success = new CategorySuccess();
        if(categoryRepository.findById(id).isEmpty()){
            success.setIsSuccessfull(false);
            success.setMessage("Category not found!");
        } else {
            Category retrievedCategory = categoryRepository.findById(id).get();
            success.setCategory(retrievedCategory);
            success.setIsSuccessfull(true);
        }
        return success;
    }

    public SuccessObject deleteCategory(Category category) {
        SuccessObject success = new CategorySuccess();
        category.setActive(0);
        this.categoryRepository.save(category);
        if(categoryRepository.findById(category.getCategoryId()).get().getActive()==0){
            success.setIsSuccessfull(true);
            success.setMessage("Category "+category.getCategoryName()+" (ID: "+category.getCategoryId()+" ) was successfully removed");
        } else{
            success.setIsSuccessfull(false);
            success.setMessage("Category " + category.getCategoryName() + " (ID: " + category.getCategoryId() + ")" + " could not be removed.");
        }
        return success;
    }

}
