package com.g1springboard.ArogyaPolicy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.g1springboard.ArogyaPolicy.model.EnrollmentStatus;
import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.service.FeedbackService;
import com.g1springboard.ArogyaPolicy.service.MyUserService;
import com.g1springboard.ArogyaPolicy.service.PolicyService;
import com.g1springboard.ArogyaPolicy.service.SchemeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
@RequestMapping("/admin/policy")
public class PolicyController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired 
    private PolicyService policyService;

    @Autowired
    private SchemeService schemeService;

    @Autowired 
    private MyUserService myUserService;

    @GetMapping()
    public String getAllPolicies(Model model) {
    List<Scheme> schemes = schemeService.getAllScheme();
    model.addAttribute("schemes", schemes);

        return "admin-policyall";
    }

    @GetMapping("/view/{id}")
    public String viewPolicy(@PathVariable Long id, Model model) {
    Policy policy = policyService.getPolicyDetails(id); // Fetch the policy by ID
    List<Feedback> feedback = feedbackService.getFeedbackByPolicy(id);
    model.addAttribute("policy", policy);
    model.addAttribute("feedback", feedback);
    return "view-policy"; // This should return the view-policy template above
}


    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createPolicyForm(Model model) {
        model.addAttribute("policy", new Policy());
        model.addAttribute("schemes", schemeService.getAllScheme());
        return "policy-form";
    }

    @PostMapping("/save")
    public String savePolicy(@ModelAttribute Policy policy) {
        policyService.createPolicy(policy);
        return "redirect:/admin/policy"; // Redirect to the controller ,apping
    }

    @GetMapping("/edit/{id}")
    public String editPolicyForm(@PathVariable("id") Long id, Model model) {
        Policy policy = policyService.getPolicyDetails(id);
        model.addAttribute("policy", policy);
        model.addAttribute("schemes", schemeService.getAllScheme());
        return "policy-form";
    }


    @DeleteMapping("/delete") //HARD DELETE
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePolicy(@PathVariable Long id ){
        policyService.deletePolicy(id);
        return "policy deleted";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivatePolicy(@PathVariable Long id) {
        policyService.closePolicy(id);
        // If policy is not found, redirect to the policies list with an error message
        return "redirect:/admin/policy";
    }


@GetMapping("/requests")
public String viewPolicyRequests(Model model) {
    // Fetch all policies with status PENDING
    List<Policy> pendingPolicies = policyService.getAllPendingPolicies();

    if (pendingPolicies.isEmpty()) {
        model.addAttribute("message", "No pending policy requests at the moment.");
    }

    model.addAttribute("requests", pendingPolicies);
    return "admin-policy-requests"; // Corresponds to the Thymeleaf page
}

@PostMapping("/ok/{id}/update")
public String updatePolicyStatus(@PathVariable Long id, @RequestParam String action, RedirectAttributes redirectAttributes) {
    Policy policy = policyService.getPolicyDetails(id);
    if (policy==null) {
        redirectAttributes.addFlashAttribute("error", "Policy not found!");
        return "redirect:/admin/policy/requests";
    }
    // Approve or reject the policy
    if ("approve".equalsIgnoreCase(action)) {
        policy.setEnrollmentStatus(EnrollmentStatus.APPROVED);
        redirectAttributes.addFlashAttribute("success", "Policy approved!");
    } else if ("reject".equalsIgnoreCase(action)) {
        policy.setEnrollmentStatus(EnrollmentStatus.REJECTED);
        redirectAttributes.addFlashAttribute("success", "Policy rejected!");
    } else {
        redirectAttributes.addFlashAttribute("error", "Invalid action!");
        return "redirect:/admin/policy/requests";
    }

    policyService.createPolicy(policy);
    return "redirect:/admin/policy/requests";
}


    @GetMapping("getPolicy/{id}")
    public Policy policyDetails(@PathVariable Long id) {
        return policyService.getPolicyDetails(id);

    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Policy> getPoliciesByUserId( @PathVariable Long id) {
        return policyService.getPoliciesByUserId(id);
    }


    
    



    
    


}
