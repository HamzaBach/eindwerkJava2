package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "SELECT * FROM category WHERE active = 1", nativeQuery = true)
    List<Category> findAllActiveCategories();
    boolean existsCategoryByCategoryNameAndActive(String categoryname, int active);
    boolean existsCategoryByCategoryAbbreviationAndActive(String categoryAbbreviation, int active);
    Optional<Category> findByCategoryNameAndActive(String categoryName, int active);
    Optional<Category> findByCategoryAbbreviationAndActive(String categoryAbbreviation, int active);

}
