package com.example.my_streaming.Domain.Transactions.Card;

import com.example.my_streaming.Domain.Account.User.User;
import com.example.my_streaming.Domain.Transactions.Transaction.Transaction;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_card_")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String card_number;
    private Double available_limit;

    private Boolean active_card;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany (mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Double getAvailable_limit() {
        return available_limit;
    }

    public void setAvailable_limit(Double available_limit) {
        this.available_limit = available_limit;
    }

    public Boolean getActive_card() {
        return active_card;
    }

    public void setActive_card(Boolean active_card) {
        this.active_card = active_card;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
