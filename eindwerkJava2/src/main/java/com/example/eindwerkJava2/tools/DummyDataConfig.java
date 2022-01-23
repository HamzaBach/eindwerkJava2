//package com.example.eindwerkJava2.tools;
//
//import com.example.eindwerkJava2.model.Article;
//import com.example.eindwerkJava2.repositories.ArticleRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DummyDataConfig {
//    @Bean
//    CommandLineRunner commandLineRunner (ArticleRepository articleRepository){
//return args ->{
//    Article article1 = new Article("Iphone 12",
//            "The 12th generation of the Iphone is here,...",
//            1L,1L);
//    articleRepository.save(article1);
//
//    Article article2 = new Article("Airpods", "Apple airpods offer wireless sound...",
//            2L,2L);
//    articleRepository.save(article2);
//
//    Article article3 = new Article("Dell XPS 15", "The new Dell XPS15 laptop for the poweruser...",
//            3L,3L);
//    articleRepository.save(article3);
//
//};
//    }
//}
