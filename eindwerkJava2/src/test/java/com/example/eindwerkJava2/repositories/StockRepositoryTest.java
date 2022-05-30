package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StockRepositoryTest {
    @Autowired
    StockRepository stockRepositoryTest;
    @Autowired
    ArticleRepository articleRepositoryTest;
    @Autowired
    CategoryRepository categoryRepositoryTest;
    @Autowired
    LocationRepository locationRepositoryTest;
    @Autowired
    LocationTypeRepository locationTypeRepositoryTest;
    @Autowired
    WarehouseRepository warehouseRepositoryTest;

    private Stock toBeTestedStock;

    @BeforeEach
    void setUp() {
        //Given
        //// Defining warehouses:
        Warehouse store = new Warehouse("Physical store");
        ////LocationTypes & Locations:
        LocationType loadingDock = new LocationType("Loading dock", false);
        ////Defining a loading dock for store & storage:
        Location storeLoadingDock = new Location(store.getWarehouseName() + ": DOCK 01", store, loadingDock);
        ////Defining category & article:
        Category smartphone = new Category("Smartphone", "SMTPH");
        Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
        Article article1 = new Article("Iphone 12",
                "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                smartphone, "Iph12");
        article1.setArticleBarcode("SMTPH-Iph12-1");
        Article article2 = new Article("Airpods", "AirPods zijn licht van gewicht en hebben een aansluitende pasvorm.",
                smartphoneAccessoires, "APDS");
        ////Defining stock:
        Stock stock1 = new Stock(article1,100.00,storeLoadingDock,1);
        Stock stock2= new Stock(article2,50.00,storeLoadingDock,0);


        //When
        warehouseRepositoryTest.save(store);
        locationTypeRepositoryTest.save(loadingDock);
        locationRepositoryTest.save(storeLoadingDock);
        categoryRepositoryTest.save(smartphone);
        categoryRepositoryTest.save(smartphoneAccessoires);
        articleRepositoryTest.save(article1);
        articleRepositoryTest.save(article2);
        stockRepositoryTest.save(stock1);
        stockRepositoryTest.save(stock2);

        toBeTestedStock = stockRepositoryTest.findByArticle(articleRepositoryTest.findByArticleName("Iphone 12").get());
    }

    @AfterEach
    void tearDown() {
        warehouseRepositoryTest.deleteAll();
        locationTypeRepositoryTest.deleteAll();
        locationRepositoryTest.deleteAll();
        categoryRepositoryTest.deleteAll();
        articleRepositoryTest.deleteAll();
        stockRepositoryTest.deleteAll();
    }

    @Test
    void activeStock_Should_Give_Iphone12() {
            assertEquals("Iphone 12",stockRepositoryTest.activeStock().get(0).getArticle().getArticleName());
    }

    @Test
    void findStockByArticleIdAndLocationId_Should_Give_Iphone12() {
        long articleId = toBeTestedStock.getArticle().getArticleId();
        String articleName = toBeTestedStock.getArticle().getArticleName();
        long locationId = stockRepositoryTest.findByArticle(toBeTestedStock.getArticle()).getLocation().getLocationId();
        assertEquals(articleName,stockRepositoryTest.findStockByArticleIdAndLocationId(articleId, locationId).get().getArticle().getArticleName());
    }
}