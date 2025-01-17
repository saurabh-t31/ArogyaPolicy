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

    public Claim createClaim(Claim claim,String userEmail){
        Optional<MyUser> user = myUserRepo.findByEmail(userEmail);
        if(!user.isPresent()) throw new RuntimeException("User not found");
              
        Policy policy = policyRepo.findById(claim.getPolicy().getId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        claim.setUser(user.get());
        claim.setPolicy(policy);
        claim.setClaimStatus("Pending");
        claim.setClaimDate(LocalDate.now());

        return claimRepo.save(claim);
       
    }
    
    public List<Claim> getAllClaims() {
        return claimRepo.findAll();
    }

    public Claim getClaimDetails(Long claimId) {
        return claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    // Admin updates claim status
    public Claim updateClaimStatus(Long claimId, String status) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setClaimStatus(status);
        return claimRepo.save(claim);
    }
    
    public List<Claim> getUserClaims(String email){
        return claimRepo.findByUserEmail(email);
    }


}
