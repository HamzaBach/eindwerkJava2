package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
public class BarcodeController {

    @Autowired
    private final BarcodeService barcodeService;

    public BarcodeController (BarcodeService barcodeService){
        this.barcodeService=barcodeService;
    }


//    @GetMapping(value = "/article/barcode/{articleId}")
//    public ResponseEntity<BufferedImage> getBarcodeArticle(@PathVariable("articleId") Long articleId) {
//        return barcodeService.generateArticleBarcodeImage(articleId);
//    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @GetMapping("/article/barcode/{articleId}")
    @ResponseBody
    void showBarcode(@PathVariable("articleId") Long articleId, HttpServletResponse response)
            throws IOException {
        BufferedImage barcode = barcodeService.generateArticleBarcodeImage(articleId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(barcode, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        if(barcode!=null){
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(bytes);
            response.getOutputStream().close();
        }
    }




}
