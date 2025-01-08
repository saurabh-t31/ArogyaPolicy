package com.g1springboard.ArogyaPolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g1springboard.ArogyaPolicy.model.Scheme;

@Repository
public interface SchemeRepo extends JpaRepository<Scheme , Long>  {
    
}
