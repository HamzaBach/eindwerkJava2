package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.ImageBlob;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.SupplierRepository;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import com.example.eindwerkJava2.wrappers.SupplierSuccess;
import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ImageService imageService;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierSuccess getAllSuppliers() {
        SupplierSuccess success = new SupplierSuccess();
        List<Supplier> suppliersList = supplierRepository.findAllActiveUsers();
        if (suppliersList.size() > 0) {
            success.setSuppliers(suppliersList);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("No suppliers have been found within the database!");
        }
        return success;
    }

    public SupplierSuccess getSupplierById(Long id) {
        SupplierSuccess success = new SupplierSuccess();
        Boolean existsSupplierById = supplierRepository.existsById(id);
        if (existsSupplierById) {
            Supplier supplier = supplierRepository.getById(id);
            success.setSupplier(supplier);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("No supplier has been found with id: " + id);
        }
        return success;
    }

    @Override
    public SuccessObject saveSupplier(Supplier supplier) {
        SuccessObject isSaveSuccessful=new SupplierSuccess();
        Boolean existsSupplierByName = supplierRepository.existsSupplierBySupplierName(supplier.getSupplierName());
        if(existsSupplierByName){
            Supplier supplierWithSameName = supplierRepository.findById(supplier.getSupplierId()).orElse(null);
            // use case if a new supplier gets named to the name of an already present supplier name -> block!
            if(supplier.getSupplierId()==null && supplierWithSameName.getActiveSupplier()==1){
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("New supplier cannot be added because this supplier name " + supplier.getSupplierName() + " already exists!");
                return isSaveSuccessful;
            }
            // use case if an existing supplier gets renamed to the name of an already present supplier name -> block!
            if (supplier.getSupplierId() != null
                    && supplierWithSameName.getSupplierId() != supplier.getSupplierId()
                    && supplierWithSameName.getActiveSupplier() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("Cannot modify this supplier because the supplier name " + supplier.getSupplierName() + " already exists!");
                return isSaveSuccessful;
            }
        }
        this.supplierRepository.save(supplier);
        isSaveSuccessful.setIsSuccessfull(true);
        isSaveSuccessful.setMessage("Supplier "+supplier.getSupplierName()+" is successfully saved");
        return isSaveSuccessful;
    }

    @Override
    public SupplierSuccess findById(Long id) {
        SupplierSuccess success = new SupplierSuccess();
        Boolean existsSupplierById = supplierRepository.existsById(id);
        if (existsSupplierById) {
            Supplier supplier = supplierRepository.findById(id).orElse(null);
            success.setSupplier(supplier);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("No supplier has been found with id: " + id);
        }
        return success;
    }

    @Override
    public SuccessObject deleteSupplier(Supplier supplier) {
        SuccessObject deleteSuccess = new SupplierSuccess();
        supplier.setActiveSupplier(0);
        this.supplierRepository.save(supplier);
        if(supplierRepository.findById(supplier.getSupplierId()).get().getActiveSupplier()==0){
            //TODO add logic if a supplier has articleSuppliers attached to it, should we delete them as well???
            deleteSuccess.setIsSuccessfull(true);
            deleteSuccess.setMessage("Supplier " + supplier.getSupplierName() + " (ID: " + supplier.getSupplierId() + ")" + " was successfully removed.");
        } else {
            deleteSuccess.setIsSuccessfull(false);
            deleteSuccess.setMessage("Supplier " + supplier.getSupplierName() + " (ID: " + supplier.getSupplierId() + ")" + " could not been removed.");
        }
       return deleteSuccess;
    }

    @Override
    public void makePdf() {
        List<Supplier> suppliers = getAllSuppliers().getSuppliers();
        List<ImageBlob> images = imageService.getImages();

        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document,
                    new FileOutputStream("Supplier.pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add a paragraph to the document

            document.add(new Paragraph(suppliers.get(0).getSupplierName().toUpperCase()));
            document.add(new Paragraph(suppliers.get(0).getAdress()));
            document.add(new Paragraph(suppliers.get(0).getCity().getCityZipcode() + " " + suppliers.get(0).getCity().getCityName()));
            document.add(new Paragraph(suppliers.get(0).getCountry().getCountryName() + "\n" + "\n"));
            document.add(new Paragraph("SYNTRAPXL"));
            document.add(new Paragraph("Thor Park 8040"));
            document.add(new Paragraph("3600 Genk"));
            document.add(new Paragraph("BELGIË"));


            byte[] byteArrayImage = imageService.getImages().get(0).getImageLob();
            Image image = Image.getInstance(byteArrayImage);
            image.setAbsolutePosition(350, 650);
            document.add(image);

            Table table = new Table(4);
            table.hasBorders();
            table.setPadding(2);
            //headers
            Cell name = new Cell("NAME");
            name.setHeader(true);
            name.setColspan(1);
            table.addCell(name);
            Cell address = new Cell("ADDRESS");
            address.setHeader(true);
            address.setColspan(1);
            table.addCell(address);
            Cell city = new Cell("CITY");
            city.setHeader(true);
            city.setColspan(1);
            table.addCell(city);
            Cell country = new Cell("COUNTRY");
            country.setHeader(true);
            country.setColspan(1);
            table.addCell(country);
            table.endHeaders();

            //body

            for (Supplier supplier : suppliers) {
                table.addCell(supplier.getSupplierName());
                table.addCell(supplier.getAdress());
                table.addCell(supplier.getCity().getCityName());
                table.addCell(supplier.getCountry().getCountryName());
            }
            document.add(table);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        // step 5: we close the document
        document.close();
    }
}
