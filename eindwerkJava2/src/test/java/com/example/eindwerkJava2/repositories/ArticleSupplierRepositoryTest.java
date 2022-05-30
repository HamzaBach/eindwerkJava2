package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArticleSupplierRepositoryTest {
    @Autowired
    private ArticleRepository articleRepositoryTest;
    @Autowired
    CategoryRepository categoryRepositoryTest;
    @Autowired
    SupplierRepository supplierRepositoryTest;
    @Autowired
    CitiesRepository citiesRepositoryTest;
    @Autowired
    CountriesRepository countriesRepositoryTest;
    @Autowired
    ArticleSupplierRepository articleSupplierRepositoryTest;

    Supplier initializeDbWithData() {
        //given
        Category smartphone = new Category("Smartphone", "SMTPH");
        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
        Category laptopAccesoires = new Category("Laptops", "LPTP");
        categoryRepositoryTest.save(smartphone);
        categoryRepositoryTest.save(smartphoneAccessoires);
        categoryRepositoryTest.save(laptopAccesoires);

        Article article1 = new Article("Iphone 12",
                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                smartphone, "Iph12");
        article1.setArticleBarcode("SMTPH-Iph12-1");
        Article article2 = new Article("Airpods", "AirPods zijn licht van gewicht en hebben een aansluitende pasvorm.",
                smartphoneAccessoires, "APDS");
        Article article3 = new Article("Nokia 3310", "De Nokia 3310 is een mobiele telefoon van het type dual-band GSM900/1800, gefabriceerd door de Finse fabrikant Nokia. ",
                laptopAccesoires, "NK3310");
        Article article4 = new Article("Iphone 13",
                "We hebben de architectuur totaal vernieuwd en de lenzen 45 graden gedraaid.",
                smartphone, "Iph13");
        Article article5 = new Article("Motorola Razr",
                "Van binnen beschikt razr over krachtige en efficiënte technologie. De Qualcomm® Snapdragon™ 710-processor is ontworpen om aan al je behoeftes te voldoen.",
                smartphone, "Rzr01");
        articleRepositoryTest.save(article1);
        articleRepositoryTest.save(article2);
        articleRepositoryTest.save(article3);
        articleRepositoryTest.save(article4);
        articleRepositoryTest.save(article5);

        City Genk = new City(3600, "Genk", "Limburg");
        City Koln = new City(4235, "Köln", "NordRhein-Westfalen");
        City Stockholm = new City(1000, "Stockholm", "Stockholm");
        citiesRepositoryTest.save(Genk);
        citiesRepositoryTest.save(Koln);
        citiesRepositoryTest.save(Stockholm);

        Country Belgium = new Country("Belgium", "BE");
        Country Germany = new Country("Germany", "DE");
        Country Sweden = new Country("Sweden", "SV");
        countriesRepositoryTest.save(Belgium);
        countriesRepositoryTest.save(Germany);
        countriesRepositoryTest.save(Sweden);

        Supplier Apple = new Supplier("Apple", "Nieuwstraat 14", Genk, Belgium, 1);
        Supplier Motorola = new Supplier("Motorola", "NeuStrasse 14", Koln, Germany, 1);
        Supplier Nokia = new Supplier("Nokia", "Ragnarstreet 12", Stockholm, Sweden, 1);
        supplierRepositoryTest.save(Apple);
        supplierRepositoryTest.save(Motorola);
        supplierRepositoryTest.save(Nokia);

        //When
        ArticleSupplier appleProduct1 = new ArticleSupplier(article1, Apple, "IPH12-2019-06", 640.00, 500.00, 1);
        ArticleSupplier appleProduct2 = new ArticleSupplier(article4, Apple, "IPH13-2021-06", 750.00, 650.00, 1);
        ArticleSupplier appleProduct3 = new ArticleSupplier(article2, Apple, "AIP3-2019-06", 199.00, 150.00, 0);
        ArticleSupplier nokiaProduct1 = new ArticleSupplier(article3, Nokia, "N3310-2007-02", 150.00, 100.00, 0);
        ArticleSupplier motorolaProduct1 = new ArticleSupplier(article5, Motorola, "Rzr010-2009-02", 350.00, 300.00, 1);
        articleSupplierRepositoryTest.save(appleProduct1);
        articleSupplierRepositoryTest.save(appleProduct2);
        articleSupplierRepositoryTest.save(appleProduct3);
        articleSupplierRepositoryTest.save(nokiaProduct1);
        articleSupplierRepositoryTest.save(motorolaProduct1);
        return Apple;
    }

    @Test
    void getAllArticleSuppliers_Should_Give_Three() {
        initializeDbWithData();
        //then
        assertEquals(3, articleSupplierRepositoryTest.getAllArticleSuppliers().size());
    }


    @Test
    void getActiveArticlesFromSpecificSupplier_Should_Give_Two() {
        Supplier Apple = initializeDbWithData();
        //then
        assertEquals(2, articleSupplierRepositoryTest.getActiveArticlesFromSpecificSupplier(Apple.getSupplierId()).size());
    }

    @AfterEach
    void tearDown() {
        articleRepositoryTest.deleteAll();
        categoryRepositoryTest.deleteAll();
        supplierRepositoryTest.deleteAll();
        citiesRepositoryTest.deleteAll();
        countriesRepositoryTest.deleteAll();
        articleSupplierRepositoryTest.deleteAll();
    }
}