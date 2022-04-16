package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    public List<Supplier> getAllSuppliers();
    void saveSupplier(Supplier supplier);
    public Optional<Supplier> findById(long id);
    public void deleteSupplier(Supplier supplier);
    void makePdf();


    }
