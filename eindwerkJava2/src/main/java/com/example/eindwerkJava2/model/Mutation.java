package com.example.eindwerkJava2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Mutation {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long mutationId;
    @ManyToOne
    @JoinColumn(name="articleId")
    private Article article;
    private Double amount;
    private String comment;

    @ManyToOne
    @JoinColumn(name="locationFrom")
    private Location locationFrom;
    @ManyToOne
    @JoinColumn(name="locationTo")
    private Location locationTo;
    @ManyToOne
    @JoinColumn(name="warehouseFrom")
    private Warehouse warehouseFrom;
    @ManyToOne
    @JoinColumn(name="warehouseTo")
    private Warehouse warehouseTo;

//     1 locatie mag maar 1 artikel bevatten
//    artikel mag maar op 1 locatie staan in de fysieke winkel. In magazijn mogen er meerdere locaties zijn rekeninghoudnde met bovenstaande regel
//     Laatste amount stock nemen en die dan meteen aanpassen, niet itereren door mutations
    @ManyToOne
    @JoinColumn(name="transactionTypeId")
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public Mutation(){}

}
