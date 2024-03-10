package com.example.my_streaming.Requests;

import com.example.my_streaming.Domain.Transactions.Card.Card;
import jakarta.validation.constraints.NotNull;


public class CreateUserRequest {
    @NotNull
    private String name;
    @NotNull
    private Long planId;
    @NotNull
    private CardRequest card;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public CardRequest getCard() {
        return card;
    }

    public void setCard(CardRequest card) {
        this.card = card;
    }

}

