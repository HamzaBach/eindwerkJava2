package com.example.eindwerkJava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
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
    @Lob //Added for solving issues for too long strings.
    private String comment;

    @ManyToOne
    @JoinColumn(name="location")
    private Location Location;

//     1 locatie mag maar 1 artikel bevatten
//    artikel mag maar op 1 locatie staan in de fysieke winkel. In magazijn mogen er meerdere locaties zijn rekeninghoudnde met bovenstaande regel
//     Laatste amount stock nemen en die dan meteen aanpassen, niet itereren door mutations
    @ManyToOne
    @JoinColumn(name="transactionTypeId")
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    private LocalDateTime localDateTime;


    public Mutation(Article article, Double amount, String comment, Location location, TransactionType transactionType, User user, LocalDateTime localDateTime) {
        this.article = article;
        this.amount = amount;
        this.comment = comment;
        this.Location = location;
        this.transactionType = transactionType;
        this.user = user;
        this.localDateTime = localDateTime;
    }

}
