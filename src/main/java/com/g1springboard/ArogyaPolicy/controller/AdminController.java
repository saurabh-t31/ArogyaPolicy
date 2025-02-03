package com.g1springboard.ArogyaPolicy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.g1springboard.ArogyaPolicy.model.Claim;
import com.g1springboard.ArogyaPolicy.model.ClaimStatus;
import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.service.ClaimService;
import com.g1springboard.ArogyaPolicy.service.FeedbackService;
import com.g1springboard.ArogyaPolicy.service.MyUserService;
import com.g1springboard.ArogyaPolicy.service.SchemeService;


@Controller
@RequestMapping("/admin")
public class AdminController {

     @Autowired 
    private ClaimService claimService;

    @Autowired 
    private SchemeService schemeService;
      
    @Autowired
    private MyUserService myUserService;

    @Autowired
    private FeedbackService feedbackService;
    


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String getAdminDashboard(){
        return "adminHome";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getallusers")
    public List<MyUser> getallUsers(){
        return myUserService.getallmyusers();
    }

    @GetMapping("/claims")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewClaims(Model model) {
        List<Claim> claims = claimService.getAllClaims();
        model.addAttribute("claims", claims);
        return "admin-claim-list"; // View to list all claims
    }

     @PostMapping("/claim/{claimId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateClaimStatus(@PathVariable Long claimId, @RequestParam String action, @RequestParam String message, Model model) {

        Claim claim = claimService.getClaimById(claimId);

        if (action.equalsIgnoreCase("approve")) {
            claim.setClaimStatus(ClaimStatus.APPROVED);
        } else if (action.equalsIgnoreCase("reject")) {
            claim.setClaimStatus(ClaimStatus.REJECTED);
        }

        claim.setAdminActionMessage(message);
        claimService.saveClaim(claim);

        model.addAttribute("message", "Claim updated successfully.");
        return "redirect:/admin/claims";
    }
    

    @GetMapping("/feedback")
    public String viewAllFeedbacks( Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        List<Scheme>  schemes =  schemeService.getAllScheme();
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("schemes", schemes);
        return "admin-feedback-list";
    }

     // Admin: View Feedback by Policy
    @GetMapping("/feedback/policy/{policyId}")
    public String viewFeedbackByPolicy(@PathVariable Long policyId, Model model) {
        List<Feedback> feedbacks = feedbackService.getFeedbackByPolicy(policyId);
        model.addAttribute("feedbacks", feedbacks);
        return "admin-feedback-list";
    }

    @GetMapping("/feedback/scheme/{schemeId}")
    public String viewFeedbackByScheme(@PathVariable Long schemeId, Model model) {
        List<Feedback> feedbacks = feedbackService.getFeedbackByScheme(schemeId);
        model.addAttribute("feedbacks", feedbacks);
        return "admin-feedback-list";
    }

    
    


}
