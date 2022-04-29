package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Article;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Method to verify if setting/getting a list of articles was successful or not
 *
 * @author Hamza
 * @version 1.0
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ArticlesSuccess extends SuccessObject {
    private List<Article> articles = new ArrayList<>();

}
