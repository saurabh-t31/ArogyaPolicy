package com.g1springboard.ArogyaPolicy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.repository.PolicyRepo;

@Service
public class PolicyService {
    
    @Autowired
    private PolicyRepo policyRepo ;

    public Policy createPolicy(Policy policy) {
        return policyRepo.save(policy);
    }

}
