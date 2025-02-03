package com.g1springboard.ArogyaPolicy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.Claim;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.repository.ClaimRepo;
import com.g1springboard.ArogyaPolicy.repository.MyUserRepo;
import com.g1springboard.ArogyaPolicy.repository.PolicyRepo;

@Service
public class ClaimService {
    
    @Autowired
    private PolicyRepo policyRepo;

    @Autowired
    private ClaimRepo claimRepo;

    @Autowired
    private MyUserRepo myUserRepo;

    
    public void saveClaim(Claim claim) {
        claimRepo.save(claim);
    }

    public Claim getClaimById(Long claimId) {
        return claimRepo.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    public List<Claim> getAllClaims() {
        return claimRepo.findAll();
    }

    public List<Claim> getClaimsOfUser(Long id){
        return claimRepo.findByUserId(id);
    }
    public List<Claim> getClaimsByStatus(String status) {
        return claimRepo.findByClaimStatus(status);
    }
}
