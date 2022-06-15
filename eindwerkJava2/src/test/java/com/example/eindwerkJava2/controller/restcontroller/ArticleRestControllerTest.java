package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.model.dto.ArticleDto;
import com.example.eindwerkJava2.repositories.*;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static io.restassured.http.ContentType.HTML;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.properties")
class ArticleRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleSupplierRepository articleSupplierRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @Test
    void whenArticlesAsked_thenShowArticles() {
//        //Given
//        Category smartphone = new Category("Smartphone", "SMTPH");
//        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
//        categoryRepository.save(smartphoneAccessoires);
//        categoryRepository.save(smartphone);
//
//        Article article = new Article("Iphone 12",
//                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
//                smartphone, "Iph12");
//        article.setArticleBarcode("SMTPH-Iph12-2");
//        articleRepository.save(article);
//
//
//        City city = new City(3600, "Genk", "Limburg");
//        citiesRepository.save(city);
//        Country country = new Country("Belgium", "BE");
//        countriesRepository.save(country);
//        Supplier supplier = new Supplier("name", "address", city, country, 1);
//        supplierRepository.save(supplier);
//
//
//        ArticleSupplier articleSupplier = new ArticleSupplier(article, supplier, "dfdsf",
//                3, 4, 1);
//
//        articleSupplierRepository.save(articleSupplier);
//
//
//
//        Article byArticleBarcode = articleRepository.findByArticleBarcode("SMTPH-Iph12-2").get();
//        ArticleSupplier articleSupplier1 = articleSupplierRepository.findById(1L).get();
//        byArticleBarcode.setArticleSupplier(articleSupplier1);
//
//        articleRepository.save(byArticleBarcode);
//
//
////        List<Article> all = articleRepository.findAll();
////        for(Article article1: all){
////            System.out.println(article1.getArticleName());
////        }
//
//                RestAssured
//                        .given()
//                        .contentType(JSON)
//                        .when()
//                        .port(port)
//                        .accept(JSON)
//                        .get("/api/v1/articles")
//                        .then()
//                        .assertThat()
//                        .statusCode(HttpStatus.OK.value());
//

    }

    @Test
    void givenBarcode_whenArticlesAsked_thenShowArticles() {
//        //Given
//        Category smartphone = new Category("Smartphone", "SMTPH");
//        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
//        categoryRepository.save(smartphoneAccessoires);
//        categoryRepository.save(smartphone);
//
//        Article article = new Article("Iphone 12",
//                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
//                smartphone, "Iph12");
//        article.setArticleBarcode("SMTPH-Iph12-2");
//        articleRepository.save(article);
//
//
//        City city = new City(3600, "Genk", "Limburg");
//        citiesRepository.save(city);
//        Country country = new Country("Belgium", "BE");
//        countriesRepository.save(country);
//        Supplier supplier = new Supplier("name", "address", city, country, 1);
//        supplierRepository.save(supplier);
//
//
//        ArticleSupplier articleSupplier = new ArticleSupplier(article, supplier, "dfdsf",
//                3, 4, 1);
//
//        articleSupplierRepository.save(articleSupplier);
//
//
//
//        Article byArticleBarcode = articleRepository.findByArticleBarcode("SMTPH-Iph12-2").get();
//        ArticleSupplier articleSupplier1 = articleSupplierRepository.findById(1L).get();
//        byArticleBarcode.setArticleSupplier(articleSupplier1);
//
//        articleRepository.save(byArticleBarcode);
//
//
////        List<Article> all = articleRepository.findAll();
////        for(Article article1: all){
////            System.out.println(article1.getArticleName());
////        }
//        ArticleDto  articleDto =
//                RestAssured
//                        .given()
//                        .contentType(HTML)
//                        .when()
//                        .port(port)
//                        .accept(HTML)
//                        .get("/api/v1/articles/SMTPH-Iph12-2")
//                        .then()
//                        .assertThat()
//                        .statusCode(HttpStatus.OK.value())
//                        .extract()
//                        .as(ArticleDto.class);
//
//        Assertions.assertThat(articleDto.getArticle()).isEqualTo("Iphone 12");
    }

}