package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.repositories.OrderSupplierHeaderRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderSupplierHeaderService {
    @Autowired
    final OrderSupplierHeaderRepository orderSupplierHeaderRepository;
    @Autowired
    ImageService imageService;

    public OrderSupplierHeaderService(OrderSupplierHeaderRepository orderSupplierHeaderRepository) {
        this.orderSupplierHeaderRepository = orderSupplierHeaderRepository;
    }

    public SuccessEvaluator<OrderSupplierHeader> getOrderSupplierHeaders() {
        SuccessEvaluator<OrderSupplierHeader> orderSupplierHeaderSuccess = new SuccessEvaluator<>();
        List<OrderSupplierHeader> orderSupplierHeaderList = orderSupplierHeaderRepository.findAll();
        if (orderSupplierHeaderList.size() > 0) {
            orderSupplierHeaderSuccess.setIsSuccessfull(true);
            orderSupplierHeaderSuccess.setEntities(orderSupplierHeaderList);
        } else {
            orderSupplierHeaderSuccess.setIsSuccessfull(false);
            orderSupplierHeaderSuccess.setMessage("No order supplier headers found within the database.");
        }
        return orderSupplierHeaderSuccess;
    }

    public SuccessEvaluator<OrderSupplierHeader> findById(Long id) {
        SuccessEvaluator<OrderSupplierHeader> success = new SuccessEvaluator<>();
        boolean existsOrderSupplierHeader = orderSupplierHeaderRepository.existsById(id);
        if (existsOrderSupplierHeader) {
            OrderSupplierHeader orderSupplierHeader = orderSupplierHeaderRepository.findById(id).orElse(null);
            success.setEntity(orderSupplierHeader);
            success.setIsSuccessfull(true);
        } else {
            success.setMessage("Order not found!");
            success.setIsSuccessfull(false);
        }
        return success;
    }

    public Long getMaxId() {
        return orderSupplierHeaderRepository.getMaxId();
    }


    public SuccessEvaluator<OrderSupplierHeader> save(OrderSupplierHeader orderSupplierHeader) {
        SuccessEvaluator<OrderSupplierHeader> isSaveSuccessful = new SuccessEvaluator<>();
        orderSupplierHeaderRepository.save(orderSupplierHeader);
        Optional<OrderSupplierHeader> orderSupplierHeader1 = orderSupplierHeaderRepository.findById(orderSupplierHeaderRepository.getMaxId());
        String supplierName = orderSupplierHeader1.get().getSupplier().getSupplierName();
        orderSupplierHeader1.get().setOrderNumber(supplierName + "-" + orderSupplierHeader1.get().getOrderSupplierId());
        orderSupplierHeaderRepository.save(orderSupplierHeader1.get());

        Boolean existsOrderNumber = orderSupplierHeaderRepository.existsByOrderNumber(orderSupplierHeader1.get().getOrderNumber());
        if (existsOrderNumber) {
            isSaveSuccessful.setIsSuccessfull(true);
        } else {
            isSaveSuccessful.setIsSuccessfull(false);
            isSaveSuccessful.setMessage("Unable to save/retrieve the order number ("+orderSupplierHeader1.get().getOrderNumber()+") within the database!");
        }
        return isSaveSuccessful;
    }

    public SuccessObject closeOrder(Long orderHeaderId) {
        SuccessObject isCloseOrderSuccessful = new SuccessEvaluator<>();
        OrderSupplierHeader orderSupplierHeader = orderSupplierHeaderRepository.findById(orderHeaderId).get();
        orderSupplierHeader.setDateOrderClosed(LocalDate.now());
        SuccessEvaluator<OrderSupplierHeader> isSaveSuccessful = save(orderSupplierHeader);
        if(orderSupplierHeaderRepository.findByOrderNumber(orderSupplierHeader.getOrderNumber()).getDateOrderClosed()!=null && isSaveSuccessful.getIsSuccessfull()){
            isCloseOrderSuccessful.setIsSuccessfull(true);
            isCloseOrderSuccessful.setMessage("Order "+orderSupplierHeader.getOrderNumber()+" was successfully closed");
        }else{
            isCloseOrderSuccessful.setIsSuccessfull(false);
            isCloseOrderSuccessful.setMessage("Unable to close the order!");
        }
        return isCloseOrderSuccessful;
    }

    public SuccessEvaluator<OrderSupplierHeader> getAllClosedOrders() {
        SuccessEvaluator<OrderSupplierHeader> success = new SuccessEvaluator<>();
        List<OrderSupplierHeader> orderSupplierHeaderList=orderSupplierHeaderRepository.getAllClosedOrders();

        if(orderSupplierHeaderList.size()>0){
            success.setEntities(orderSupplierHeaderList);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("No closed orders found within the database");
        }
        return success;
    }


    public void makePdf(Long orderHeaderId, List<OrderSupplierDetail> orderDetailList) {
        OrderSupplierHeader header = orderSupplierHeaderRepository.findById(orderHeaderId).get();

        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream("order-" + LocalDate.now() + "-" + header.getOrderNumber() + ".pdf"));
            // step 3: we open the document
            document.open();
            // step 4: we add a paragraph to the document

            document.add(new Paragraph(header.getSupplier().getSupplierName().toUpperCase()));
            document.add(new Paragraph(header.getSupplier().getAdress()));
            document.add(new Paragraph(header.getSupplier().getCity().getCityZipcode() + " " + header.getSupplier().getCity().getCityName().toUpperCase()));
            document.add(new Paragraph(header.getSupplier().getCountry().getCountryName().toUpperCase() + "\n" + "\n"));
            document.add(new Paragraph("SYNTRAPXL"));
            document.add(new Paragraph("Thor Park 8040"));
            document.add(new Paragraph("3600 Genk"));
            document.add(new Paragraph("BELGIË"));

            document.add(new Paragraph("\n" + "\n" + "Ordernumber: " + header.getOrderNumber()));


//            byte[] byteArrayImage = imageService.getImages().get(0).getImageLob();
//            Image image = Image.getInstance(byteArrayImage);
//            image.setAbsolutePosition(350,650);
//            document.add(image);


            Table table = new Table(4);
            table.hasBorders();
            table.setPadding(2);
            //headers
            Cell name = new Cell("ORDERLINE");
            name.setHeader(true);
            name.setColspan(1);
            table.addCell(name);
            Cell address = new Cell("ARTICLE");
            address.setHeader(true);
            address.setColspan(1);
            table.addCell(address);
            Cell city = new Cell("QUANTITY");
            city.setHeader(true);
            city.setColspan(1);
            table.addCell(city);
            Cell country = new Cell("EXPECTED DATE OF DELIVERY");
            country.setHeader(true);
            country.setColspan(1);
            table.addCell(country);
            table.endHeaders();

            //body

            for (OrderSupplierDetail orderdetail : orderDetailList) {
                table.addCell(orderdetail.getOrderlineNumber());
                table.addCell(orderdetail.getArticle().getArticleName());
                table.addCell(String.valueOf(orderdetail.getExpectedQuantity()));
                table.addCell(orderdetail.getExpectedDayOfDelivery().toString());
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

    public OrderSupplierHeader findByOrderNumber(String orderNumber) {
        return orderSupplierHeaderRepository.findByOrderNumber(orderNumber);
    }
}

