package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Category;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepositoryTest;

    @BeforeEach
    void dummyData(){
        Category smartphone = new Category("Smartphone", "SMTPH");
        smartphone.setActive(0);
        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
        smartphoneAccessoires.setActive(1);
        Category laptopAccesoires = new Category("Laptops", "LPTP");
        laptopAccesoires.setActive(1);
        categoryRepositoryTest.save(smartphone);
        categoryRepositoryTest.save(smartphoneAccessoires);
        categoryRepositoryTest.save(laptopAccesoires);
    }

    @Test
    void findAllActiveCategories_Should_Give_Two() {
        //Given+When see dummyData
        //Then
        assertEquals(2,categoryRepositoryTest.findAllActiveCategories().size());

    }

    @AfterEach
    void tearDown() {
        categoryRepositoryTest.deleteAll();
    }
}