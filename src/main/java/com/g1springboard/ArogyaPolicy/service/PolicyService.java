package com.g1springboard.ArogyaPolicy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.EnrollmentStatus;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.model.PolicyStatus;
import com.g1springboard.ArogyaPolicy.repository.PolicyRepo;

@Service
public class PolicyService {
    
    @Autowired
    private PolicyRepo policyRepo ;

    public List<Policy> allPolicies(){
        return policyRepo.findAll();
    }

    public Policy createPolicy(Policy policy) {
        return policyRepo.save(policy);
    }

    public Policy updatePolicy(Long userId ,Policy updatedPolicy){
          
        Optional<Policy> existingPolicy = policyRepo.findById(userId);

        if(!existingPolicy.isPresent()) throw new RuntimeException("Policy Not Found");

        existingPolicy.get().setName(updatedPolicy.getName());
        existingPolicy.get().setDescription(updatedPolicy.getDescription());
        existingPolicy.get().setTotalPremiumAmount(updatedPolicy.getTotalPremiumAmount());
        existingPolicy.get().setMaturityAmount(updatedPolicy.getMaturityAmount());
        existingPolicy.get().setNumberOfYears(updatedPolicy.getNumberOfYears());
        existingPolicy.get().setPolicyStatus(updatedPolicy.getPolicyStatus());
        existingPolicy.get().setAnnuityTerm(updatedPolicy.getAnnuityTerm());


        if (updatedPolicy.getScheme() != null) {
            existingPolicy.get().setScheme(updatedPolicy.getScheme());
        }

        // Save updated policy
        return policyRepo.save(existingPolicy.get());
    }

    public void deletePolicy(Long id){
        policyRepo.deleteById(id);
    }
     
    public void closePolicy(Long id){
        
        Policy policy = policyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        policy.setPolicyStatus(PolicyStatus.INACTIVE);
        System.out.println("Closing Policy with Status: " + policy.getPolicyStatus());

        policyRepo.save(policy);
    }

    public Policy getPolicyDetails(Long id) {
           return policyRepo.findById(id)
                  .orElseThrow(() -> new RuntimeException("Policy Not Found"));

    }

    public List<Policy> getPoliciesByUserId(Long userId) {
        return policyRepo.findByUserId(userId);
    }

    public List<Policy> getAllPendingPolicies() {
    return policyRepo.findByEnrollmentStatus(EnrollmentStatus.PENDING);
}


    
    

}
