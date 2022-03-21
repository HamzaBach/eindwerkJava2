package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.*;
import java.awt.image.BufferedImage;


@Service
public class BarcodeService {

    @Autowired
    private final ArticleService articleService;

    public BarcodeService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public BufferedImage generateArticleBarcodeImage(@PathVariable("articleId") Long articleId) {
        Article article = articleService.findById(articleId).get();
        String barcode = article.getArticleBarcode();
        if(!barcode.isEmpty()){
            Code128Bean barcodeGenerator = new Code128Bean();
            BitmapCanvasProvider canvas =
                    new BitmapCanvasProvider(640, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            barcodeGenerator.generateBarcode(canvas, article.getArticleBarcode());
            return canvas.getBufferedImage();
        }else{
            return null;
        }
    }
}
