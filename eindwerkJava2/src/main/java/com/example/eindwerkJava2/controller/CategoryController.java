package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }



    @GetMapping("/category")
    public String viewCategories(Model model){
        model.addAttribute("categoriesList", categoryService.getCategories());
        return "category";
    }

    @GetMapping("/showNewCategoryForm")
    public String showNewCategoryForm(Model model){
        Category category = new Category();

        model.addAttribute("category", new Category());

        return "form_category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category category){
        this.categoryService.addCategory(category);
        return "redirect:/category";
    }


    @GetMapping("editCategory/{categoryId}")
    public String showEditCategoryForm(@PathVariable("categoryId") Long categoryId, Model model){
        Category category = categoryService.findById(categoryId).get();
        model.addAttribute("category", category);

        return "form_category";
    }

    @GetMapping("deleteCategory/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId, Model model){
        Category category = categoryService.findById(categoryId).get();
        this.categoryService.deleteCategory(category);
        return "redirect:/category";
    }

}
