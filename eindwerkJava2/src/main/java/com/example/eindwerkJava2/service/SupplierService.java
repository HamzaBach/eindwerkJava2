package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;

public interface SupplierService {
    public SuccessEvaluator<Supplier> getAllSuppliers();
    SuccessObject saveSupplier(Supplier supplier);
    public SuccessEvaluator<Supplier> findById(Long id);
    public SuccessObject deleteSupplier(Supplier supplier);
    void makePdf();


    }
