package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.City;
import com.example.eindwerkJava2.model.Country;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class OrderSupplierHeaderRepositoryTest {
    @Autowired
    CitiesRepository citiesRepositoryTest;
    @Autowired
    CountriesRepository countriesRepositoryTest;
    @Autowired
    SupplierRepository supplierRepositoryTest;
    @Autowired
    OrderSupplierHeaderRepository orderSupplierHeaderTest;


    @BeforeEach
    void setUp() {
        //Given
        City Genk = new City(3600, "Genk", "Limburg");
        citiesRepositoryTest.save(Genk);
        Country Belgium = new Country("Belgium", "BE");
        countriesRepositoryTest.save(Belgium);
        Supplier Apple = new Supplier("Apple", "Nieuwstraat 14", Genk, Belgium, 1);
        supplierRepositoryTest.save(Apple);
        LocalDate localDate = LocalDate.now();

        OrderSupplierHeader orderSupplierHeaderClosed = new OrderSupplierHeader();
        orderSupplierHeaderClosed.setSupplier(Apple);
        orderSupplierHeaderClosed.setDateOfOrder(localDate);
        orderSupplierHeaderClosed.setOrderNumber("TestOrder Closed");
        orderSupplierHeaderClosed.setDateOrderClosed(localDate);
        orderSupplierHeaderClosed.setDateOrderReceived(localDate);

        OrderSupplierHeader orderSupplierHeaderOpen = new OrderSupplierHeader();
        orderSupplierHeaderOpen.setSupplier(Apple);
        orderSupplierHeaderOpen.setDateOfOrder(localDate);
        orderSupplierHeaderOpen.setOrderNumber("TestOrder Open");
        orderSupplierHeaderOpen.setDateOrderClosed(localDate);

        //When
        orderSupplierHeaderTest.save(orderSupplierHeaderClosed);
        orderSupplierHeaderTest.save(orderSupplierHeaderOpen);
    }

    @AfterEach
    void tearDown() {
        citiesRepositoryTest.deleteAll();
        countriesRepositoryTest.deleteAll();
        supplierRepositoryTest.deleteAll();
        orderSupplierHeaderTest.deleteAll();
    }

    @Test
    void getMaxId_Should_Give_Two() {
        //Then
        assertEquals(2,orderSupplierHeaderTest.getMaxId());
    }

    @Test
    void getAllClosedOrders_Should_Give_One() {
        //Then
        assertEquals(1,orderSupplierHeaderTest.getAllClosedOrders().size());
    }
}