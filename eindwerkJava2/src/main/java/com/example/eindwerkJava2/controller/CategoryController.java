package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Categories")

public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {return categoryService.getCategories();}

    @PostMapping(path = "newCategory")
    public void addCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
    }

}
