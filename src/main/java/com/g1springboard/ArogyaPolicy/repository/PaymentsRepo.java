package com.g1springboard.ArogyaPolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g1springboard.ArogyaPolicy.model.Payment;

public interface PaymentsRepo extends JpaRepository<Payment ,Long>{
    
}
