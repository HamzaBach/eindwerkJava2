package com.example.eindwerkJava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sale_header")
public class SaleHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleHeaderId;
    @Column(name = "date_of_sale")
    private LocalDateTime dateOfOrder;
    @Column(name = "name_sales_person")
    private String nameSalesPerson;

    public SaleHeader(String nameSalesPerson) {
        this.nameSalesPerson = nameSalesPerson;
    }

}
