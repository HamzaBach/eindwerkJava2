package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.repositories.MutationRepository;
import com.example.eindwerkJava2.repositories.StockRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


class MutationServiceImplTest {

    @Mock
    private  MutationRepository mutationRepository;
    private AutoCloseable autoCloseable;


    private MutationServiceImpl mutationServiceImpl;
    private StockService stockService;

    @BeforeEach
    void setUP(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        mutationServiceImpl = new MutationServiceImpl(mutationRepository,stockService);


    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void addStock() {

    }

    @Test
    void GetAllMutations(){
        //WHEN
        mutationServiceImpl.getMutations();
        //THEN
        verify(mutationRepository.findAll());
    }

    @Test
    void removeStock() {
    }

    @Test
    void moveStock() {
    }
}