package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.service.CategoryService;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }


    @GetMapping("/category")
    public String viewCategories(Model model){
        SuccessEvaluator success = categoryService.getCategories();
        if(success.getIsSuccessfull()){
            model.addAttribute("categoriesList", success.getEntities());
        } else{
            model.addAttribute("error",success.getMessage());
        }
        return "category";
    }

    @GetMapping("/new/category")
    public String showNewCategoryForm(Model model){
        model.addAttribute("category", new Category());
        return "/forms/form_category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category category, RedirectAttributes redirAttrs, Model model){
        SuccessObject isSaveSuccessfull = this.categoryService.addCategory(category);
        if(isSaveSuccessfull.getIsSuccessfull()){
            redirAttrs.addFlashAttribute("success",isSaveSuccessfull.getMessage());
            return "redirect:/category";
        } else {
            model.addAttribute("error", isSaveSuccessfull.getMessage());
            return "/forms/form_category";
        }
    }


    @GetMapping("edit/category/{categoryId}")
    public String showEditCategoryForm(@PathVariable("categoryId") Long categoryId, Model model){
        SuccessEvaluator<Category> success = categoryService.findById(categoryId);
        if(success.getIsSuccessfull()){
            Category category = success.getEntity();
            model.addAttribute("category", category);
        } else {
            model.addAttribute("error", success.getMessage());
        }
        return "/forms/form_category";
    }

    @GetMapping("delete/category/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId, RedirectAttributes redirAttrs){
        SuccessEvaluator<Category> findCategory = categoryService.findById(categoryId);
        if(findCategory.getIsSuccessfull()){
            SuccessObject toBeDeletedCategory = this.categoryService.deleteCategory(findCategory.getEntity());
            if(toBeDeletedCategory.getIsSuccessfull()){
                redirAttrs.addFlashAttribute("success", toBeDeletedCategory.getMessage());
            } else{
                redirAttrs.addFlashAttribute("error", toBeDeletedCategory.getMessage());
            }
        } else {
            redirAttrs.addFlashAttribute("error", findCategory.getMessage());
        }
        return "redirect:/category";
    }

}
