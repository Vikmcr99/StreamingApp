package com.example.my_streaming.Domain.Transactions.Transaction;

import com.example.my_streaming.Domain.Transactions.Card.Card;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private Date date;
    private double transaction_value;
    private String merchant;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTransaction_value() {
        return transaction_value;
    }

    public void setTransaction_value(double transaction_value) {
        this.transaction_value = transaction_value;
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
