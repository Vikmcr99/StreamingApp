package com.example.my_streaming.Requests;

import com.example.my_streaming.Domain.Transactions.Card.Card;


public class CreateUserRequest {

    private String name;

    private Long planId;

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

