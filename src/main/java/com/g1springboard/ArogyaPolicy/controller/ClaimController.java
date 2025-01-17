package com.g1springboard.ArogyaPolicy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.Claim;
import com.g1springboard.ArogyaPolicy.service.ClaimService;

@RestController
@RequestMapping("/claims")
public class ClaimController {
    
    @Autowired
    private ClaimService claimService;

    @PostMapping("/createclaim")
    @PreAuthorize("hasRole('USER')")
    public Claim createClaim(@RequestBody Claim claim, Principal principal) {
        return claimService.createClaim(claim, principal.getName());
    }

    // User views their claims
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public List<Claim> getUserClaims(Principal principal) {
        return claimService.getUserClaims(principal.getName());
    }

    @GetMapping("/claims")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Claim> getAllClaims() {
        return claimService.getAllClaims();
    }

    // Admin updates claim status
    @PutMapping("/{claimId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Claim updateClaimStatus(@PathVariable Long claimId, @RequestParam String status) {
        return claimService.updateClaimStatus(claimId, status);
    }


        





}
