package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    //List<Category> findByCategoryId(long category_id);
    //boolean existsCategoryId(long category_id);
}
