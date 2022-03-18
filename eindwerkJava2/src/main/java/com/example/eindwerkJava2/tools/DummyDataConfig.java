package com.example.eindwerkJava2.tools;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyDataConfig {
    @Bean
    CommandLineRunner commandLineRunner (ArticleRepository articleRepository,
                                         ArticleSupplierRepository articleSupplierRepository,
                                         CategoryRepository categoryRepository,
                                         CitiesRepository citiesRepository,
                                         CountriesRepository countriesRepository,
                                         SupplierRepository supplierRepository,
                                         WarehouseRepository warehouseRepository,
                                         LocationRepository locationRepository){
        return args ->{
            Category smartphone = new Category(1L, "Smartphone", "SMTPH");
            Category smartphoneAccessoires = new Category(2L, "Smartphone Accesoires","SMTPH-ACC");
            Category laptopAccesoires = new Category(3L, "Laptops","LPTP");
            categoryRepository.save(smartphone);
            categoryRepository.save(smartphoneAccessoires);
            categoryRepository.save(laptopAccesoires);

            Cities Genk = new Cities(3600, "Genk", "Limburg" );
            Cities Koln = new Cities(4235, "Köln", "NordRhein-Westfalen" );
            Cities Stockholm = new Cities(1000, "Stockholm", "Stockholm" );
            citiesRepository.save(Genk);
            citiesRepository.save(Koln);
            citiesRepository.save(Stockholm);

            Countries Belgium = new Countries("Belgium","BE");
            Countries Germany = new Countries("Germany","DE");
            Countries Sweden = new Countries("Sweden", "SV");
            countriesRepository.save(Belgium);
            countriesRepository.save(Germany);
            countriesRepository.save(Sweden);

            Supplier Apple = new Supplier("Apple","Nieuwstraat 14",Genk,Belgium,1);
            Supplier Siemens = new Supplier("Siemens","NeuStrasse 14",Koln,Germany,1);
            Supplier Nokia = new Supplier("Nokia","Ragnarstreet 12",Stockholm,Sweden,1);
            supplierRepository.save(Apple);
            supplierRepository.save(Siemens);
            supplierRepository.save(Nokia);

            Article article1 = new Article("Iphone 12",
                    "The 12th generation of the Iphone is here,...",
                    smartphone,"Iph12");
            Article article2 = new Article("Airpods", "Apple airpods offer wireless sound...",
                    smartphoneAccessoires,"APDS");
            Article article3 = new Article("Nokia 3310", "Unbreakable phone...",
                    laptopAccesoires,"NK3310");
            articleRepository.save(article1);
            articleRepository.save(article2);
            articleRepository.save(article3);
        };
    }
}
