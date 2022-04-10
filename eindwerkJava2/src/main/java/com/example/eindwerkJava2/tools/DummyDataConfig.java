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
            Supplier Motorola = new Supplier("Motorola","NeuStrasse 14",Koln,Germany,1);
            Supplier Nokia = new Supplier("Nokia","Ragnarstreet 12",Stockholm,Sweden,1);
            dummySuppliers.add(Apple);
            dummySuppliers.add(Motorola);
            dummySuppliers.add(Nokia);
            for(Supplier supplier:dummySuppliers){
                if(!supplierRepository.existsSupplierBySupplierName(supplier.getSupplierName())){
                    supplierRepository.save(supplier);
                }
            }

            List<Article> dummyArticles = new ArrayList<Article>();
            Article article1 = new Article("Iphone 12",
                    "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                    smartphone,"Iph12");
            article1.setArticleBarcode("SMTPH-Iph12-1");
            Article article2 = new Article("Airpods", "AirPods zijn licht van gewicht en hebben een aansluitende pasvorm.",
                    smartphoneAccessoires,"APDS");
            article2.setArticleBarcode("SMTPH-ACC-APDS-2");
            Article article3 = new Article("Nokia 3310", "De Nokia 3310 is een mobiele telefoon van het type dual-band GSM900/1800, gefabriceerd door de Finse fabrikant Nokia. ",
                    laptopAccesoires,"NK3310");
            article3.setArticleBarcode("LPTP-NK3310-3");
            Article article4 = new Article("Iphone 13",
                    "We hebben de architectuur totaal vernieuwd en de lenzen 45 graden gedraaid.",
                    smartphone,"Iph13");
            article4.setArticleBarcode("SMTPH-Iph13-4");
            Article article5= new Article("Motorola Razr",
                    "Van binnen beschikt razr over krachtige en efficiënte technologie. De Qualcomm® Snapdragon™ 710-processor is ontworpen om aan al je behoeftes te voldoen.",
                    smartphone,"Rzr01");
            article5.setArticleBarcode("SMTPH-Rzr01-5");

            dummyArticles.add(article1);
            dummyArticles.add(article2);
            dummyArticles.add(article3);
            dummyArticles.add(article4);
            dummyArticles.add(article5);
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
            User user1 = new User("Abdeljalil","$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user2 = new User("Hamza","$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user3 = new User("Rinaldo","$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user4 = new User("Sebastiaan", "$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user5 = new User("Johan","$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
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
            for(User user:dummyUsers){
                if(userRepository.findByUserName(user.getUserName()).getRoles().isEmpty()){
                    user.addOneRole(roleRepository.findByName("ADMIN"));
                    userRepository.save(user);
                }
            }

            ArticleSupplier appleProduct1 = new ArticleSupplier(article1,Apple,"IPH12-2019-06",640.00,1);
            ArticleSupplier appleProduct2 = new ArticleSupplier(article4,Apple,"IPH13-2021-06",750.00,1);
            ArticleSupplier appleProduct3 = new ArticleSupplier(article2,Apple,"AIP3-2019-06",199.00,1);
            ArticleSupplier nokiaProduct1 = new ArticleSupplier(article3,Nokia,"N3310-2007-02",150.00,1);
            ArticleSupplier motorolaProduct1 = new ArticleSupplier(article5,Motorola,"Rzr010-2009-02",350.00,1);
            List<ArticleSupplier> dummyArticleSuppliers=new ArrayList<ArticleSupplier>();
            dummyArticleSuppliers.add(appleProduct1);
            dummyArticleSuppliers.add(appleProduct2);
            dummyArticleSuppliers.add(appleProduct3);
            dummyArticleSuppliers.add(nokiaProduct1);
            dummyArticleSuppliers.add(motorolaProduct1);
            for(ArticleSupplier articleSupplier:dummyArticleSuppliers){
                if((articleSupplierRepository.count()<5)){
                    articleSupplierRepository.save(articleSupplier);
                }
            }
        };
    }
}
