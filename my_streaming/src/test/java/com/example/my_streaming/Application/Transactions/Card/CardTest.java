package com.example.my_streaming.Application.Transactions.Card;

import com.example.my_streaming.Application.Transactions.Transaction.Transaction;
import com.example.my_streaming.Exceptions.BusinessException;
import com.example.my_streaming.Exceptions.BusinessValidation;
import com.example.my_streaming.Exceptions.CardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setActive_card(true);
        card.setCard_number("1234567890");
        card.setAvailable_limit(1000.0);
    }

    @Test
    @DisplayName("Should create transaction")
    void createTransaction() {

        Transaction transaction = new Transaction();
        transaction.setMerchant("Merchant");
        transaction.setTransaction_value(500.0);
        transaction.setDescription("Description");
        transaction.setDate(new Date());

        card.createTransaction("Merchant", 500.0, "Description");

        assertEquals(1, card.getTransactions().size());
    }



    @Test
    @DisplayName("Should throw exception when card is not active")
    void createTransactionInactiveCard() {
        card.setActive_card(false);

        assertThrows(RuntimeException.class, () -> {
            card.createTransaction("Merchant", 500.0, "Description");
        });
    }

    @Test
    @DisplayName("Should throw exception when transaction value exceeds available limit")
    void createTransactionExceedLimit() {
        assertThrows(RuntimeException.class, () -> {
            card.createTransaction("Merchant", 1500.0, "Description");
        });
    }

    @Test
    @DisplayName("Should throw exception when too many transactions from same merchant in short period")
    void createTransactionTooManyTransactions() {

        for (int i = 0; i < 3; i++) {
            Transaction transaction = new Transaction();
            transaction.setMerchant("Merchant");
            transaction.setTransaction_value(100.0);
            transaction.setDate(new Date(System.currentTimeMillis() - i * 60 * 1000)); // Within last 5 minutes
            card.getTransactions().add(transaction);
        }

        assertThrows(RuntimeException.class, () -> {
            card.createTransaction("Merchant", 100.0, "Description");
        });
    }

    @Test
    @DisplayName("Should throw exception when transaction value exceeds available limit")
    void createTransactionExceedsAvailableLimit() {
        Card card = new Card();
        card.setActive_card(true);
        card.setAvailable_limit(100.0);


        assertThrows(RuntimeException.class, () -> {
            card.createTransaction("Merchant", 200.0, "Description"); // Valor da transação maior que o limite disponível
        });
    }

}