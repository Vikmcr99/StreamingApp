package com.example.my_streaming.Domain.Transactions.Plan;

import com.example.my_streaming.Domain.Transactions.Subscription.Subscription;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long Id;
    private String name;
    private String description;
    private double plan_value;
    @OneToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    public Plan(long id, String name, String description, double plan_value, Subscription subscription) {
        Id = id;
        this.name = name;
        this.description = description;
        this.plan_value = plan_value;
        this.subscription = subscription;
    }

    public Plan() {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPlan_value() {
        return plan_value;
    }

    public void setPlan_value(double plan_value) {
        this.plan_value = plan_value;
    }
}
