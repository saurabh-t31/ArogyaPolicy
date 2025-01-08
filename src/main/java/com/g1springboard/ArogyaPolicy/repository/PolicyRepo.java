package com.g1springboard.ArogyaPolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g1springboard.ArogyaPolicy.model.Policy;

@Repository
public interface PolicyRepo extends JpaRepository<Policy, Long> {
    
}
