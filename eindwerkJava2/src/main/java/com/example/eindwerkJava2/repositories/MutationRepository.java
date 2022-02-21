package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Mutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MutationRepository extends JpaRepository<Mutation,Long> {



}
