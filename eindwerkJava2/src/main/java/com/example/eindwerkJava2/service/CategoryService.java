package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.repositories.CategoryRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public SuccessEvaluator<Category> getCategories() {
        SuccessEvaluator<Category> success = new SuccessEvaluator<>();
        List<Category> retrievedCategories = categoryRepository.findAllActiveCategories();
        if (retrievedCategories.isEmpty()) {
            success.setIsSuccessfull(false);
            success.setMessage("No categories where found, please add categories!");
        } else {
            success.setIsSuccessfull(true);
            success.setEntities(retrievedCategories);
        }
        return success;
    }

    public SuccessEvaluator<Category> addCategory(Category category) {
        SuccessEvaluator<Category> isSaveSuccessful = new SuccessEvaluator<>();
        boolean existsCategoryByCategoryName = categoryRepository.existsCategoryByCategoryNameAndActive(category.getCategoryName(),1);
        if (existsCategoryByCategoryName) {
            Category categoryWithSameName = categoryRepository.findByCategoryNameAndActive(category.getCategoryName(),1).get();
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
        boolean existsCategoryAbbreviation = categoryRepository.existsCategoryByCategoryAbbreviationAndActive(category.getCategoryAbbreviation(),1);
        if (existsCategoryAbbreviation) {
            Category categoryWithSameAbbreviation = categoryRepository.findByCategoryAbbreviationAndActive(category.getCategoryAbbreviation(),1).get();
            // use case if a new category gets named to the name of an already present category abbreviation -> block!
            if (category.getCategoryId() == null
                    && categoryWithSameAbbreviation.getActive() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("New category cannot be added because this category abbreviation " + category.getCategoryAbbreviation() + " already exists!");
                return isSaveSuccessful;
            }
            // use case if an existing category gets renamed to the name of an already present category abbreviation -> block!
            if (category.getCategoryId() != null
                    && categoryWithSameAbbreviation.getCategoryId() != category.getCategoryId()
                    && categoryWithSameAbbreviation.getActive() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("Cannot modify this category because the category abbreviation " + category.getCategoryAbbreviation() + " already exists!");
                return isSaveSuccessful;
            }
        }
        categoryRepository.save(category);
        isSaveSuccessful.setIsSuccessfull(true);
        isSaveSuccessful.setMessage("Category " + category.getCategoryName() + " is successfully saved!");
        return isSaveSuccessful;
    }


    public SuccessEvaluator<Category> findById(Long id) {
        SuccessEvaluator<Category> success = new SuccessEvaluator<>();
        if(categoryRepository.findById(id).isEmpty()){
            success.setIsSuccessfull(false);
            success.setMessage("Category not found!");
        } else {
            Category retrievedCategory = categoryRepository.findById(id).get();
            success.setEntity(retrievedCategory);
            success.setIsSuccessfull(true);
        }
        return success;
    }

    public SuccessObject deleteCategory(Category category) {
        SuccessObject success = new SuccessEvaluator<>();
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
