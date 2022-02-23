package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.image.BufferedImage;

@Controller
public class BarcodeController {

    @Autowired
    private final BarcodeService barcodeService;

    public BarcodeController (BarcodeService barcodeService){
        this.barcodeService=barcodeService;
    }


    @GetMapping(value = "/article/barcode/{articleId}")
    public ResponseEntity<BufferedImage> getBarcodeArticle(@PathVariable("articleId") Long articleId) {
        return barcodeService.generateArticleBarcodeImage(articleId);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }



}
