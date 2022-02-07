package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }

    @Override
    public void saveSupplier(Supplier supplier){
        this.supplierRepository.save(supplier);
    }
    @Override
    public Optional<Supplier> findById(long id){
        return supplierRepository.findById(id);
    }
    @Override
    public void deleteSupplier(Supplier supplier){
        supplier.setActiveSupplier(0);
        this.supplierRepository.save(supplier);
    }
}
