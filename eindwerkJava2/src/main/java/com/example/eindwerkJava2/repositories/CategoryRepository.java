package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "SELECT * FROM category WHERE active = 1", nativeQuery = true)
    List<Category> findAllActiveCategories();
}
