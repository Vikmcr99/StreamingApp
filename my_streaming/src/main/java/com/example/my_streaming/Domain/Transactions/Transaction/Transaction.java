package com.example.my_streaming.Domain.Transactions.Transaction;

import com.example.my_streaming.Domain.Transactions.Card.Card;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    private Date date;
    private double value;
    private String merchant;
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "card_id")
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
