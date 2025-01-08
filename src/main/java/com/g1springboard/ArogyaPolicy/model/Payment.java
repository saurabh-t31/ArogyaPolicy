package com.g1springboard.ArogyaPolicy.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PaymentsId;
    
    private LocalDate PaymentsDate;
    private Double amount;
    private String PaymentsType;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;
    
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

}
