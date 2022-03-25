package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.ImageBlob;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.SupplierRepository;
import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Optional;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAllActiveUsers();
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

    @Override
    public  void makePdf() {
        List<Supplier> suppliers = getAllSuppliers();
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
            document.add(new Paragraph(suppliers.get(0).getCity().getCityZipcode()+ " "  + suppliers.get(0).getCity().getCityName()));
            document.add(new Paragraph(suppliers.get(0).getCountry().getCountryName()+"\n"+"\n"));
            document.add(new Paragraph("SYNTRAPXL"));
            document.add(new Paragraph("Thor Park 8040"));
            document.add(new Paragraph("3600 Genk"));
            document.add(new Paragraph("BELGIË"));


            byte[] byteArrayImage = imageService.getImages().get(0).getImageLob();
            Image image = Image.getInstance(byteArrayImage);
            image.setAbsolutePosition(350,650);
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

            for(Supplier supplier: suppliers){
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
