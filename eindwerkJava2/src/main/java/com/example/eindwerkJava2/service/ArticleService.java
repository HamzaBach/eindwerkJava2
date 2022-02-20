package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private final ArticleRepository articleRepository;


    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getActiveArticles() {
        return this.articleRepository.findByActiveArticle(1);
    }

    public Article getArticle(Long articleId){
        if(articleRepository.existsArticleByArticleId(articleId)){
            return articleRepository.findByArticleId(articleId);
        } else throw new IllegalStateException("Article with id " + articleId + " does not exist!");
    }

    public Boolean addArticle(String articleName, String articleDescription, Category category,
                              ArticleSupplier articleSupplier, byte[] articleImage, int activeArticle) {

        if (articleRepository.existsArticleByArticleName(articleName)
                && articleRepository.existsArticleByArticleDescription(articleDescription)
                && articleRepository.existsArticleByCategory(category)) {
            return false;
        } else{
            Article newArticle = new Article(articleName,articleDescription,category, articleImage, activeArticle);
            articleRepository.save(newArticle);
            return true;
        }
    }
    public void saveArticle(Article article, byte[] articleImage){
        if(articleImage.length==0)
        {
            Article currentArticle = articleRepository.getById(article.getArticleId());
            article.setArticleImage(currentArticle.getArticleImage());
        }else{
            article.setArticleImage(articleImage);
        }
        articleRepository.save(article);
    }

    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }

    public void deleteArticle(Article article){
        article.setActiveArticle(0);
        this.articleRepository.save(article);
    }

    public byte[] getBarcodeImage(Long id) throws BarcodeException, OutputException, IOException {
        Article article = articleRepository.findByArticleId(id);
        if(article.getArticleBarcode()!=null){
            Barcode barcode = BarcodeFactory.createCode128(article.getArticleBarcode());
//      Method to alter the font: barcode.setFont(BARCODE_TEXT_FONT);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(BarcodeImageHandler.getImage(barcode),"png",baos);
            byte[] byteBarcodeImage = baos.toByteArray();
            return byteBarcodeImage;
        } else return null;
    }







}
