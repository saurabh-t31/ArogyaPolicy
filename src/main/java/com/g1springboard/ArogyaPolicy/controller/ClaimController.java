package com.g1springboard.ArogyaPolicy.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.g1springboard.ArogyaPolicy.model.Claim;
import com.g1springboard.ArogyaPolicy.model.ClaimStatus;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.service.ClaimService;
import com.g1springboard.ArogyaPolicy.service.MyUserService;
import com.g1springboard.ArogyaPolicy.service.PolicyService;

@Controller
@RequestMapping("/claim")
public class ClaimController {
    @Autowired
    private PolicyService policyService;
    
    @Autowired
    private ClaimService claimService;

    @Autowired 
    private MyUserService myUserService;

    @GetMapping("/{policyId}")
    public String showClaimForm(@PathVariable Long policyId, Model model,Principal principal, RedirectAttributes redirectAttributes) {
        Optional<MyUser> loggedInUser = myUserService.getMyUserByEmail(principal.getName());

    // Check if the user has already claimed this policy
    boolean alreadyClaimed = loggedInUser.get().getClaims().stream()
        .anyMatch(claim -> claim.getPolicy().getId().equals(policyId) 
                 && ("Approved".equalsIgnoreCase(claim.getClaimStatus().name()) || "Pending".equalsIgnoreCase(claim.getClaimStatus().name())));

    if (alreadyClaimed) {
        // Add error message and return to the form page or redirect
        redirectAttributes.addFlashAttribute("error", "You cannot apply for this policy as you already have an approved claim.");
        return "redirect:/user/my-policies"; // Redirect to a dashboard or another page
    }
        Policy policy = policyService.getPolicyDetails(policyId);
        model.addAttribute("policy", policy);

        return "claim-form"; 
    }

    @PostMapping("/request")
    @PreAuthorize("hasRole('USER')")
    public String requestClaim(@RequestParam Long policyId,@RequestParam String description, @RequestParam Double amount, Principal principal, Model model) {

        String username = principal.getName();
        Optional<MyUser> user = myUserService.getMyUserByEmail(username);

        Policy policy = policyService.getPolicyDetails(policyId);

        if (!user.isPresent()) {
            model.addAttribute("error", "User not authenticated!");
            return "redirect:/login";
        }


        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.now());
        claim.setDescription(description);
        claim.setAmount(amount);
        claim.setClaimStatus(ClaimStatus.PENDING); // Initial status is pending
        claim.setUser(user.get());
        claim.setPolicy(policy); // Assuming user has at least one policy

        claimService.saveClaim(claim);

        model.addAttribute("message", "Claim request submitted successfully.");
        return "redirect:/dashboard";
    }




 
 

}
