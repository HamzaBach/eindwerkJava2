package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The data-access layer for interacting with the database of articles.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Method to verify if an article with a particular article id exists in the database.
     * @param articleId This id is used to see whether the article is present or not in the database.
     * @return A boolean is returned to indicate if the article is present or not in the database.
     */
    boolean existsArticleByArticleId (Long articleId);

    /**
     * Method to verify if an article with a particular article name exists in the database.
     * @param articleName This name is used to see whether the article is present or not in the database.
     * @return A boolean is returned to indicate if the article is present or not in the database.
     */
    boolean existsArticleByArticleName(String articleName);

    /**
     * Method to verify if an article with a particular article name exists in the database.
     * @param articleAbbreviation This abbreviation is used to see whether an article with same abbreviation is present or not in the database.
     * @return A boolean is returned to indicate if an article is present or not with the same abbreviation in the database.
     */
    boolean existsArticleByArticleAbbreviation(String articleAbbreviation);

    /**
     * Method to return all articles in a list that have the same activeArticle value.
     * @param activeArticle An integer to indicate whether an article is active (1) or inactive (0).
     * @return A list of articles is returned that carry that activeArticle value.
     */
    List<Article> findByActiveArticle(int activeArticle);

    /**
     * Method to retrieve the maximum id of articles within the database.
     * @return The maximum used id within the article database is returned.
     */
    @Query(value = "select MAX(article_id) AS Max_Id from article",nativeQuery = true)
    Long getMaxId();

    /**
     * Method to find an article in the repository via the article name.
     * @param articleName
     * @return
     */
    Optional<Article> findByArticleName(String articleName);

    /**
     * Method to find an article in the repository via the article abbreviation.
     * @param articleAbbreviation
     * @return
     */
    Optional<Article> findByArticleAbbreviation(String articleAbbreviation);

    /**
     * Method to find an article in the repository via the barcode.
     * @param barcode
     * @return
     */
    Optional<Article> findByArticleBarcode(String barcode);

    boolean existsArticleByArticleSupplier(ArticleSupplier articleSupplier);
    @Query(value = "select * from article where article_supplier_id in (select article_supplier_id from article_supplier where supplier =?1 and active=1)", nativeQuery = true)
    List<Article> getArticlesWhereSupplierIsChosenSupplier(long supplierId);
}
