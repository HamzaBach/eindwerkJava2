package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepositoryTest;
    @Autowired
    private CategoryRepository categoryRepositoryTest;

    @Test
    void getMaxId_Should_Give_Two() {
        //Given
        Category smartphone = new Category("Smartphone", "SMTPH");
        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
        Article article1 = new Article("Iphone 12",
                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                smartphone, "Iph12");
        article1.setArticleBarcode("SMTPH-Iph12-1");
        Article article2 = new Article("Airpods", "AirPods zijn licht van gewicht en hebben een aansluitende pasvorm.",
                smartphoneAccessoires, "APDS");

        //When
        categoryRepositoryTest.save(smartphone);
        categoryRepositoryTest.save(smartphoneAccessoires);
        articleRepositoryTest.save(article1);
        articleRepositoryTest.save(article2);

        //Then
        assertEquals(2, articleRepositoryTest.getMaxId());

    }

    @AfterEach
    void tearDown() {
        articleRepositoryTest.deleteAll();
        categoryRepositoryTest.deleteAll();
    }
}