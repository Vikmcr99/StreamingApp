package com.example.my_streaming.Domain.Transactions.Card;

import com.example.my_streaming.Domain.Account.User.User;
import com.example.my_streaming.Domain.Transactions.Transaction.Transaction;
import com.example.my_streaming.Exceptions.BusinessException;
import com.example.my_streaming.Exceptions.BusinessValidation;
import com.example.my_streaming.Exceptions.CardException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_card")
public class Card {

    private static final int TRANSACTION_TIME_INTERVAL = 5;
    private static final int TRANSACTION_MERCHANT_REPEAT = 3;
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
    private List<Transaction> transactions = new ArrayList<>();

    public void createTransaction(String merchant, double value, String description) {
        CardException validationErrors = new CardException();

        isCardActive(validationErrors);

        Transaction transaction = new Transaction();
        transaction.setMerchant(merchant);
        transaction.setTransaction_value(value);
        transaction.setDescription(description);
        transaction.setDate(new Date());

        checkAvailableLimit(transaction, validationErrors);
        validateTransaction(transaction, validationErrors);

        try {
            validationErrors.validateAndThrow();
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

        transaction.setId(id);
        available_limit -= transaction.getTransaction_value();
        transactions.add(transaction);
    }

    private void isCardActive(CardException validationErrors) {
        if (!active_card) {
            validationErrors.addError(new BusinessValidation(
                    "Card is not active",
                    "Card"
            ));
        }
    }

    private void checkAvailableLimit(Transaction transaction, CardException validationErrors) {
        if (transaction.getTransaction_value() > available_limit) {
            validationErrors.addError(new BusinessValidation(
                    "Card does not have enough limit for this transaction",
                    "Card"
            ));
        }
    }

    private void validateTransaction(Transaction transaction, CardException validationErrors) {
        List<Transaction> recentTransactions = new ArrayList<>();
        for (Transaction trans : transactions) {
            if (trans.getDate().getTime() >= System.currentTimeMillis() - TRANSACTION_TIME_INTERVAL * 60 * 1000) {
                recentTransactions.add(trans);
            }
        }

        if (recentTransactions.size() >= TRANSACTION_MERCHANT_REPEAT) {
            validationErrors.addError(new BusinessValidation(
                    "Card used too many times in a short period",
                    "Card"
            ));
        }

        long merchantRepeatCount = recentTransactions.stream()
                .filter(trans -> trans.getMerchant().equalsIgnoreCase(transaction.getMerchant()) && trans.getTransaction_value() == transaction.getTransaction_value())
                .count();

        if (merchantRepeatCount >= TRANSACTION_MERCHANT_REPEAT) {
            validationErrors.addError(new BusinessValidation(
                    "Duplicate transaction for the same card and same merchant",
                    "Card"
            ));
        }
    }

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
