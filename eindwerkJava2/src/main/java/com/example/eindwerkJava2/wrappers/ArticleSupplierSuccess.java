package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.ArticleSupplier;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ArticleSupplierSuccess extends SuccessObject{
    private ArticleSupplier articleSupplier;
    private List<ArticleSupplier> articlesSuppliers;
}
