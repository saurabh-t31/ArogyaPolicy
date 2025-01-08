package com.g1springboard.ArogyaPolicy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.service.PolicyService;

@RestController
@RequestMapping("/policy")
public class PolicyController {
    
    @Autowired 
    private PolicyService policyService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Policy createPolicy(@RequestBody Policy policy){

         return policyService.createPolicy(policy);
         
    }

}
