package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Article;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Method to verify if setting/getting an article was successful or not
 *
 * @author Hamza
 * @version 1.0
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ArticleSuccess extends SuccessObject {
    private Article article;
    private List<Article> articles = new ArrayList<>();
}
