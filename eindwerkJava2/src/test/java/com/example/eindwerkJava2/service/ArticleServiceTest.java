package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import com.example.eindwerkJava2.repositories.CategoryRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepositoryTest;
    @Mock
    private CategoryRepository categoryRepositoryTest;

    private ArticleService articleServiceTest;

    private AutoCloseable autoCloseable;


    @BeforeEach
    public  void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        articleServiceTest = new ArticleService(articleRepositoryTest);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @DisplayName("Unit test for obtaining active articles:")
    @Test
    void given_ActiveArticlesList_whenGetActiveArticlesSize_thenReturnListSizeTwo() {
        //Given
        Category smartphone = new Category("Smartphone", "SMTPH");
        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
        Article article1 = new Article("Iphone 12",
                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                smartphone, "Iph12");
        article1.setArticleBarcode("SMTPH-Iph12-1");
        Article article2 = new Article("Airpods", "AirPods zijn licht van gewicht en hebben een aansluitende pasvorm.",
                smartphoneAccessoires, "APDS");

        given(articleRepositoryTest.findByActiveArticle(1)).willReturn(List.of(article1, article2));
        //When
        List<Article> activeArticles = articleServiceTest.getActiveArticles().getEntities();
        //Then
        assertThat(activeArticles).isNotNull();
        assertThat(activeArticles.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for saveArticle method")
    @Test
    void givenArticleObject_whenSaveArticle_thenReturnArticleObject() {
        //Given
        Category smartphone = new Category("Smartphone", "SMTPH");
        Article article1 = new Article("Iphone 12",
                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                smartphone, "Iph12");
        given(articleRepositoryTest.save(article1)).willReturn(article1);
        System.out.println(articleRepositoryTest);
        System.out.println(articleServiceTest);

        //When
        Article savedArticle = articleRepositoryTest.save(article1);
        System.out.println(savedArticle);

        //Then
        assertThat(savedArticle).isNotNull();
    }

    @Test
    void deleteArticle() {
        //Given
        Category smartphone = new Category("Smartphone", "SMTPH");
        Article article1 = new Article("Iphone 12",
                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                smartphone, "Iph12");
        article1.setArticleId(1L);
        SuccessObject successObject = new SuccessEvaluator();
        successObject.setIsSuccessfull(true);

        byte [] articleImage = "dummy".getBytes();


        //When

        //Then

    }
}