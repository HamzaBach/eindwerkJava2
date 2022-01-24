package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Supplier;

import java.util.List;

public interface SupplierService {
    public List<Supplier> getAllSuppliers();
    void saveSupplier(Supplier supplier);
}
