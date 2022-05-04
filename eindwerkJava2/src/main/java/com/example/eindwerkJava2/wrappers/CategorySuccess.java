package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Category;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CategorySuccess extends SuccessObject{
    private Category category;
    private List<Category> categories;
}
