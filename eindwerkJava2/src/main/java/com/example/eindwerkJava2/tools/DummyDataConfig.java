package com.example.eindwerkJava2.tools;

import com.example.eindwerkJava2.api.exchangerate.ApiExchangeRates;
import com.example.eindwerkJava2.api.exchangerate.ExchangeRates;
import com.example.eindwerkJava2.api.geo.ApiCountriesCities;
import com.example.eindwerkJava2.api.geo.json_model.City_json;
import com.example.eindwerkJava2.api.geo.json_model.Country_json;
import com.example.eindwerkJava2.api.geo.json_model.State_json;
import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.repositories.*;
import com.example.eindwerkJava2.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DummyDataConfig {
    private int randomNumberGenerator(int min, int max) {
        int minValue = min;
        int maxValue = max;
        int range = maxValue - minValue + 1;
        return (int) (Math.random() * range) + minValue;
    }

    @Bean
    CommandLineRunner commandLineRunner(ArticleRepository articleRepository,
                                        ArticleSupplierRepository articleSupplierRepository,
                                        CategoryRepository categoryRepository,
                                        CitiesRepository citiesRepository,
                                        CountriesRepository countriesRepository,
                                        StateService stateService,
                                        SupplierRepository supplierRepository,
                                        WarehouseRepository warehouseRepository,
                                        LocationRepository locationRepository,
                                        RoleRepository roleRepository,
                                        UserRepository userRepository,
                                        LocationTypeRepository locationTypeRepository,
                                        TransactionRepository transactionRepository,
                                        MutationServiceImpl mutationService,
                                        TransactionService transactionService,
                                        StateRepository stateRepository,
                                        VatService vatService,
                                        CurrencyService currencyService) {
        return args -> {
            //Define currencies:
            if (!(currencyService.getAllCurrencies().size() > 0)) {
                ApiExchangeRates apiExchangeRates = new ApiExchangeRates();
                ExchangeRates exchangeRates = apiExchangeRates.getExchangeRates("EUR");
                Set<String> currencies = exchangeRates.getConversionRates().keySet();
                for (String currency : currencies) {
                    Currency currency1 = new Currency();
                    currency1.setCurrency(currency);
                    currencyService.saveCurrency(currency1);
                }
            }

            //Define vatRates (BTW):
            List<Vat> vatRateList = new ArrayList<>();
            Vat zesPct = new Vat(0.06);
            Vat negentienPct = new Vat(0.19);
            Vat eenentwintigPct = new Vat(0.21);
            vatRateList.add(zesPct);
            vatRateList.add(negentienPct);
            vatRateList.add(eenentwintigPct);
            for (Vat vat : vatRateList) {
                if (!vatService.existsVatrate(vat)) {
                    vatService.save(vat);
                }
            }
            List<Category> dummyCategories = new ArrayList<Category>();
            Category smartphone = new Category("Smartphone", "SMTPH");
            smartphone.setVat(eenentwintigPct);
            Category smartphoneAccessoires = new Category("Smartphone Accessoires", "SMTPH-ACC");
            smartphoneAccessoires.setVat(eenentwintigPct);
            Category laptopAccesoires = new Category("Laptops", "LPTP");
            laptopAccesoires.setVat(negentienPct);
            dummyCategories.add(smartphone);
            dummyCategories.add(smartphoneAccessoires);
            dummyCategories.add(laptopAccesoires);
            for (Category category : dummyCategories) {
                if (!categoryRepository.existsCategoryByCategoryNameAndActive(category.getCategoryName(), 1)) {
                    categoryRepository.save(category);
                }
            }

            if (!(citiesRepository.findAll().size() > 0)) {
                System.out.println("****HOLD YOUR HORSES, ADDING COUNTRIES+BELGIAN STATES+BELGIAN CITIES TO THE DB (Approx a few seconds)****");
                ApiCountriesCities apiCountriesCities = new ApiCountriesCities();
                List<Country_json> countries = apiCountriesCities.getCountries();
                for (Country_json country_json : countries) {
                    Country country = country_json.convertToCountry();
                    countriesRepository.save(country);
                }
                List<State_json> states = apiCountriesCities.getStates("Belgium");

                for (State_json state_json : states) {
                    State state = state_json.convertToState();
                    state.setCountry(countriesRepository.findByCountryName("Belgium"));
                    stateService.save(state);

                    List<City_json> cities = apiCountriesCities.getCities(state_json.getState_name());
                    for (City_json city : cities) {
                        City city1 = city.convertToCity();
                        city1.setState(stateRepository.findByStateName(state.getStateName()));
                        citiesRepository.save(city1);
                    }
                }
                System.out.println("****Import of Countries/States/Cities is done.****");
            }

            List<Supplier> dummySuppliers = new ArrayList<Supplier>();
            Country Belgium = countriesRepository.findByCountryName("Belgium");
            City Genk = citiesRepository.findByCityName("Genk");
            City Hasselt = citiesRepository.findByCityName("Hasselt");
            City As = citiesRepository.findByCityName("As");
            Supplier Apple = new Supplier("Apple", "Nieuwstraat 14", Genk, Belgium, 1);
            Supplier Motorola = new Supplier("Motorola", "NeuStrasse 14", Hasselt, Belgium, 1);
            Supplier Nokia = new Supplier("Nokia", "Ragnarstreet 12", As, Belgium, 1);
            dummySuppliers.add(Apple);
            dummySuppliers.add(Motorola);
            dummySuppliers.add(Nokia);
            for (Supplier supplier : dummySuppliers) {
                if (!supplierRepository.existsSupplierBySupplierNameAndActiveSupplier(supplier.getSupplierName(), 1)) {
                    supplierRepository.save(supplier);
                }
            }

            List<Article> dummyArticles = new ArrayList<Article>();
            Article article1 = new Article("Iphone 12",
                    "Het display van iPhone 12 heeft ronde hoeken die de vorm van het stijlvolle design volgen. Deze hoeken vallen binnen een normale rechthoek. ",
                    smartphone, "Iph12");
            article1.setArticleBarcode("SMTPH-Iph12-1");
            Article article2 = new Article("Airpods", "AirPods zijn licht van gewicht en hebben een aansluitende pasvorm.",
                    smartphoneAccessoires, "APDS");
            article2.setArticleBarcode("SMTPH-ACC-APDS-2");
            Article article3 = new Article("Nokia 3310", "De Nokia 3310 is een mobiele telefoon van het type dual-band GSM900/1800, gefabriceerd door de Finse fabrikant Nokia. ",
                    laptopAccesoires, "NK3310");
            article3.setArticleBarcode("LPTP-NK3310-3");
            Article article4 = new Article("Iphone 13",
                    "We hebben de architectuur totaal vernieuwd en de lenzen 45 graden gedraaid.",
                    smartphone, "Iph13");
            article4.setArticleBarcode("SMTPH-Iph13-4");
            Article article5 = new Article("Motorola Razr",
                    "Van binnen beschikt razr over krachtige en efficiënte technologie. De Qualcomm® Snapdragon™ 710-processor is ontworpen om aan al je behoeftes te voldoen.",
                    smartphone, "Rzr01");
            article5.setArticleBarcode("SMTPH-Rzr01-5");

            dummyArticles.add(article1);
            dummyArticles.add(article2);
            dummyArticles.add(article3);
            dummyArticles.add(article4);
            dummyArticles.add(article5);
            for (Article article : dummyArticles) {
                if (!articleRepository.existsArticleByArticleNameAndActiveArticle(article.getArticleName(), 1)) {
                    articleRepository.save(article);
                }
            }

            Currency euro = currencyService.findByCurrency("EUR");

            ArticleSupplier appleProduct1 = new ArticleSupplier(article1, Apple, "IPH12-2019-06", 640.00, 500.00, euro, 1);
            ArticleSupplier appleProduct2 = new ArticleSupplier(article4, Apple, "IPH13-2021-06", 750.00, 650.00, euro, 1);
            ArticleSupplier appleProduct3 = new ArticleSupplier(article2, Apple, "AIP3-2019-06", 199.00, 150.00, euro, 1);
            ArticleSupplier nokiaProduct1 = new ArticleSupplier(article3, Nokia, "N3310-2007-02", 150.00, 100.00, euro, 1);
            ArticleSupplier motorolaProduct1 = new ArticleSupplier(article5, Motorola, "Rzr010-2009-02", 350.00, 300.00, euro, 1);
            List<ArticleSupplier> dummyArticleSuppliers = new ArrayList<ArticleSupplier>();
            dummyArticleSuppliers.add(appleProduct1);
            dummyArticleSuppliers.add(appleProduct2);
            dummyArticleSuppliers.add(appleProduct3);
            dummyArticleSuppliers.add(nokiaProduct1);
            dummyArticleSuppliers.add(motorolaProduct1);
            if (!(articleSupplierRepository.count() >0)) {
                for (ArticleSupplier articleSupplier : dummyArticleSuppliers) {
                    articleSupplierRepository.save(articleSupplier);
                    //Add default supplier to articles.
                    Article article = articleRepository.findByArticleName(articleSupplier.getArticle().getArticleName()).get();
                    if(article.getArticleSupplier()==null){
                        article.setArticleSupplier(articleSupplierRepository.getById(articleSupplier.getArticleSupplierId()));
                        articleRepository.save(article);
                    }

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
            for (Role role : defaultRoles) {
                if (!roleRepository.existsRoleByName(role.getName())) {
                    roleRepository.save(role);
                }
            }

            List<User> dummyUsers = new ArrayList<User>();
            User user1 = new User("Abdeljalil", "$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user2 = new User("Hamza", "$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user3 = new User("Rinaldo", "$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user4 = new User("Sebastiaan", "$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            User user5 = new User("Johan", "$2a$12$SdrK28X1fz10qM5NCxM5cumCCn9PRgH/G/oZIU5Wv6IvQBeLj4FG.");//password = ww
            dummyUsers.add(user1);
            dummyUsers.add(user2);
            dummyUsers.add(user3);
            dummyUsers.add(user4);
            dummyUsers.add(user5);
            for (User user : dummyUsers) {
                if (!userRepository.existsUserByUserNameAndActiveUser(user.getUserName(), 1)) {
                    userRepository.save(user);
                }
            }
            for (User user : dummyUsers) {
                if (userRepository.findByUserNameAndActiveUser(user.getUserName(), 1).getRoles().isEmpty()) {
                    user.addOneRole(roleRepository.findByName("ADMIN"));
                    userRepository.save(user);
                }
            }

            if (warehouseRepository.count() == 0 && locationRepository.count() == 0) {
                // Defining location types:
                LocationType loadingDock = new LocationType("Loading dock", false);
                LocationType shoppingCart = new LocationType("Shopping cart", false);
                LocationType singleStorage = new LocationType("Single storage", true);
                locationTypeRepository.save(loadingDock);
                locationTypeRepository.save(shoppingCart);
                locationTypeRepository.save(singleStorage);

                //Defining warehouses:
                Warehouse store = new Warehouse("Physical store");
                warehouseRepository.save(store);
                Warehouse storage = new Warehouse("Storage");
                warehouseRepository.save(storage);

                //Defining single storage locations for store & storage:
                for (int i = 0; i < 10; i++) {
                    Location location = new Location(store.getWarehouseName() + ": LOC " + i, store, singleStorage);
                    locationRepository.save(location);
                }
                for (int i = 0; i < 10; i++) {
                    Location location1 = new Location(storage.getWarehouseName() + ": LOC " + i, storage, singleStorage);
                    locationRepository.save(location1);
                }
                //Defining a loading dock for store & storage:
                Location storeLoadingDock = new Location(store.getWarehouseName() + ": DOCK 01", store, loadingDock);
                locationRepository.save(storeLoadingDock);
                Location storageLoadingDock = new Location(storage.getWarehouseName() + ": DOCK 01", storage, loadingDock);
                locationRepository.save(storageLoadingDock);
                Location storeShoppingCart = new Location(store.getWarehouseName() + ": CART 01", store, shoppingCart);
                locationRepository.save(storeShoppingCart);

                //Linking warehouses to locations?:

            }
            if (transactionRepository.count() == 0) {
                TransactionType opboeken = new TransactionType("Opboeken", 1d);
                TransactionType afboeken = new TransactionType("Afboeken", -1d);
                TransactionType correctieOpboeken = new TransactionType("Correctie opboeken", 1d);
                TransactionType correctieAfboeken = new TransactionType("Correctie afboeken", -1d);
                TransactionType intern_opboeken = new TransactionType("Intern opboeken", 1d);
                TransactionType intern_afboeken = new TransactionType("Intern afboeken", -1d);
                List<TransactionType> defaultTransType = new ArrayList<>();
                defaultTransType.add(opboeken);
                defaultTransType.add(afboeken);
                defaultTransType.add(correctieOpboeken);
                defaultTransType.add(correctieAfboeken);
                defaultTransType.add(intern_opboeken);
                defaultTransType.add(intern_afboeken);

                for (TransactionType trans : defaultTransType) {
                    if (!transactionRepository.existsByTransactionTypeName(trans.getTransactionTypeName())) {
                        transactionRepository.save(trans);
                    }
                }
            }


            //Dumping dummyData in mutations
            List<User> userList = userRepository.findByActiveUser(1);
            List<Article> articleList = articleRepository.findByActiveArticle(1);
            List<Location> singleStorageLocationsList = locationRepository.getSingleStorageLocations();
            List<Location> nonSingleStorageLocationsList = locationRepository.getNonSingleStorageLocations();
            LocalDateTime startTimeInventory = LocalDateTime.of(2021, Month.JANUARY, 1, 9, 0);


            if (mutationService.getMutations().getEntities().size() == 0) {
                //Initialize Stock to LoadingDock
                int targetLocationIndex = 0;

                for (Article article : articleList) {
                    Mutation mutation = new Mutation(article, 100.00, "Initialize Stock",
                            nonSingleStorageLocationsList.get(0),
                            transactionService.getInboundTransactionType(),
                            userList.get(randomNumberGenerator(0, userList.size() - 1)), startTimeInventory.plusHours(randomNumberGenerator(0, 8)).plusMinutes(randomNumberGenerator(0, 59)));
                    mutationService.addStock(mutation);
                    // Move stock
                    mutation.setUser(userList.get(randomNumberGenerator(0, userList.size() - 1)));
                    long targetLocationId = singleStorageLocationsList.get(targetLocationIndex).getLocationId();
                    mutation.setComment("Dummy movement");
                    mutation.setLocalDateTime(mutation.getLocalDateTime().plusDays(randomNumberGenerator(0, 5)).plusMinutes(randomNumberGenerator(0, 59)));
                    mutationService.moveStock(mutationService.createCopyOfMutation(mutation), targetLocationId);
                    targetLocationIndex++;
                    //Start selling
                    double currentAmountForSale = mutation.getAmount();
                    mutation.setLocation(locationRepository.findByLocationId(targetLocationId));
                    while (mutation.getAmount() >= 5) {
                        mutation.setComment("Dummy sale");
                        mutation.setLocalDateTime(mutation.getLocalDateTime().plusDays(randomNumberGenerator(0, 2)).plusHours(randomNumberGenerator(0, 3)).plusMinutes(randomNumberGenerator(0, 59)));
                        mutation.setAmount((double) randomNumberGenerator(1, 3));
                        currentAmountForSale -= mutation.getAmount();
                        mutationService.removeStock(mutationService.createCopyOfMutation(mutation));
                        mutation.setAmount(currentAmountForSale);
                    }

                }
            }

        };
    }
}
