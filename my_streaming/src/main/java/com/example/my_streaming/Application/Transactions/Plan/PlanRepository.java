package com.example.my_streaming.Application.Transactions.Plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("SELECT p FROM Plan p WHERE p.Id = :planId")
    Plan getPlanById(@Param("planId") Long planId);

}



