package com.example.eindwerkJava2.tools;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
                                         LocationRepository locationRepository,
                                         RoleRepository roleRepository,
                                         UserRepository userRepository){
        return args ->{
            List<Category> dummyCategories = new ArrayList<Category>();
            Category smartphone = new Category( "Smartphone", "SMTPH");
            Category smartphoneAccessoires = new Category( "Smartphone Accessoires","SMTPH-ACC");
            Category laptopAccesoires = new Category( "Laptops","LPTP");
            dummyCategories.add(smartphone);
            dummyCategories.add(smartphoneAccessoires);
            dummyCategories.add(laptopAccesoires);
            for(Category category:dummyCategories){
                if(!categoryRepository.existsCategoryByCategoryName(category.getCategoryName())){
                    categoryRepository.save(category);
                }
            }

            List<Cities> dummyCities = new ArrayList<Cities>();
            Cities Genk = new Cities(3600, "Genk", "Limburg" );
            Cities Koln = new Cities(4235, "Köln", "NordRhein-Westfalen" );
            Cities Stockholm = new Cities(1000, "Stockholm", "Stockholm" );
            dummyCities.add(Genk);
            dummyCities.add(Koln);
            dummyCities.add(Stockholm);
            for(Cities city:dummyCities){
                if(!citiesRepository.existsCitiesByCityName(city.getCityName())){
                    citiesRepository.save(city);
                }
            }

            List<Countries> dummyCountries = new ArrayList<Countries>();
            Countries Belgium = new Countries("Belgium","BE");
            Countries Germany = new Countries("Germany","DE");
            Countries Sweden = new Countries("Sweden", "SV");
            dummyCountries.add(Belgium);
            dummyCountries.add(Germany);
            dummyCountries.add(Sweden);
            for(Countries country:dummyCountries){
                if(!countriesRepository.existsCountriesByCountryName(country.getCountryName())){
                    countriesRepository.save(country);
                }
            }

            List<Supplier> dummySuppliers = new ArrayList<Supplier>();
            Supplier Apple = new Supplier("Apple","Nieuwstraat 14",Genk,Belgium,1);
            Supplier Siemens = new Supplier("Siemens","NeuStrasse 14",Koln,Germany,1);
            Supplier Nokia = new Supplier("Nokia","Ragnarstreet 12",Stockholm,Sweden,1);
            dummySuppliers.add(Apple);
            dummySuppliers.add(Siemens);
            dummySuppliers.add(Nokia);
            for(Supplier supplier:dummySuppliers){
                if(!supplierRepository.existsSupplierBySupplierName(supplier.getSupplierName())){
                    supplierRepository.save(supplier);
                }
            }

            List<Article> dummyArticles = new ArrayList<Article>();
            Article article1 = new Article("Iphone 12",
                    "The 12th generation of the Iphone is here,...",
                    smartphone,"Iph12");
            Article article2 = new Article("Airpods", "Apple airpods offer wireless sound...",
                    smartphoneAccessoires,"APDS");
            Article article3 = new Article("Nokia 3310", "Unbreakable phone...",
                    laptopAccesoires,"NK3310");
            dummyArticles.add(article1);
            dummyArticles.add(article2);
            dummyArticles.add(article3);
            for(Article article:dummyArticles){
                if(!articleRepository.existsArticleByArticleName(article.getArticleName())){
                    articleRepository.save(article);
                }
            }

            List<Role> defaultRoles = new ArrayList<Role>();
            Role role1 = new Role("USER");
            Role role2 = new Role("CREATOR");
            Role role3 = new Role("EDITOR");
            Role role4 = new Role("ADMIN");
            defaultRoles.add(role1);
            defaultRoles.add(role2);
            defaultRoles.add(role3);
            defaultRoles.add(role4);
            for(Role role:defaultRoles){
                if(!roleRepository.existsRoleByName(role.getName())){
                    roleRepository.save(role);
                }
            }

            List<User> dummyUsers = new ArrayList<User>();
            User user1 = new User("Abdeljalil","ww");
            User user2 = new User("Hamza","ww");
            User user3 = new User("Rinaldo","ww");
            User user4 = new User("Sebastiaan", "ww");
            User user5 = new User("Johan","ww");
            dummyUsers.add(user1);
            dummyUsers.add(user2);
            dummyUsers.add(user3);
            dummyUsers.add(user4);
            dummyUsers.add(user5);
            for(User user:dummyUsers){
                if(!userRepository.existsUserByUserName(user.getUserName())){
                    userRepository.save(user);
                }
            }
        };
    }
}
