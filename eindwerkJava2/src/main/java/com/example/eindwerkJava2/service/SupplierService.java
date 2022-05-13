package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import com.example.eindwerkJava2.wrappers.SupplierSuccess;

public interface SupplierService {
    public SupplierSuccess getAllSuppliers();
    SuccessObject saveSupplier(Supplier supplier);
    public SupplierSuccess findById(Long id);
    public SuccessObject deleteSupplier(Supplier supplier);
    void makePdf();


    }
