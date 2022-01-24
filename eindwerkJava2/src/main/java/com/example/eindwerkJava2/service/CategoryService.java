package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getCategories(){ return this.categoryRepository.findAll();}


    public void addCategory( Category category ){


        categoryRepository.save(category);
    }

}
