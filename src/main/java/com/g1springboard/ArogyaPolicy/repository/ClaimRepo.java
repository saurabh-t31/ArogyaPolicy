package com.g1springboard.ArogyaPolicy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g1springboard.ArogyaPolicy.model.Claim;

@Repository
public interface ClaimRepo extends JpaRepository<Claim ,Long> {
    List<Claim> findByUserEmail(String email);
    List<Claim> findByPolicyId(Long policyId);

}
