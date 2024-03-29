package com.example.my_streaming.Application.Transactions.Plan;

import com.example.my_streaming.Application.Transactions.Subscription.Subscription;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long Id;
    private String name;
    private String description;
    private double plan_value;


    public Plan(Long id, String name, String description, double plan_value) {
        Id = id;
        this.name = name;
        this.description = description;
        this.plan_value = plan_value;
    }

    public Plan() {

    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
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
