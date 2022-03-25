package com.example.eindwerkJava2.repositories;


import com.example.eindwerkJava2.model.ImageBlob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageBlob, Long> {

}
