package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Article;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ArticleSuccess extends SuccessObject {
    private Article article;

}
