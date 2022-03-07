package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    private String cardTitle;
    private String cardText;
    private int cardStatus = 0;

    public Card(Long cardId, String cardTitle, String cardText, int cardStatus) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.cardText = cardText;
        this.cardStatus = cardStatus;
    }

    public Card() {

    }


    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public int getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }
}
