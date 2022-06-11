package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.City;
import com.example.eindwerkJava2.model.Country;
import com.example.eindwerkJava2.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class SupplierRepositoryTest {
    @Autowired
    private CitiesRepository citiesRepositoryTest;
    @Autowired
    private CountriesRepository countriesRepositoryTest;
    @Autowired
    private SupplierRepository supplierRepositoryTest;


    @BeforeEach
    void setUp() {
        //Given
        ////Adding cities:
        City Genk = new City(3600, "Genk");
        City Koln = new City(4235, "Köln");
        City Stockholm = new City(1000, "Stockholm");
        ////Adding countries:
        Country Belgium = new Country("Belgium", "BE");
        Country Germany = new Country("Germany", "DE");
        Country Sweden = new Country("Sweden", "SV");
        ////Adding suppliers:
        Supplier Apple = new Supplier("Apple", "Nieuwstraat 14", Genk, Belgium, 1);
        Supplier Motorola = new Supplier("Motorola", "NeuStrasse 14", Koln, Germany, 1);
        Supplier Nokia = new Supplier("Nokia", "Ragnarstreet 12", Stockholm, Sweden, 0);

        //When
        citiesRepositoryTest.save(Genk);
        citiesRepositoryTest.save(Koln);
        citiesRepositoryTest.save(Stockholm);
        countriesRepositoryTest.save(Belgium);
        countriesRepositoryTest.save(Germany);
        countriesRepositoryTest.save(Sweden);
        supplierRepositoryTest.save(Apple);
        supplierRepositoryTest.save(Motorola);
        supplierRepositoryTest.save(Nokia);

    }

    @AfterEach
    void tearDown() {
        citiesRepositoryTest.deleteAll();
        countriesRepositoryTest.deleteAll();
        supplierRepositoryTest.deleteAll();
    }


    @Test
    void findAllActiveUsers_Should_Give_Two() {
        //Then
        assertEquals(2,supplierRepositoryTest.findAllActiveUsers().size());
    }
}