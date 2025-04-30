package com.g1springboard.ArogyaPolicy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.g1springboard.ArogyaPolicy.model.Claim;
import com.g1springboard.ArogyaPolicy.model.EnrollmentStatus;
import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.service.ClaimService;
import com.g1springboard.ArogyaPolicy.service.FeedbackService;
import com.g1springboard.ArogyaPolicy.service.MyUserService;
import com.g1springboard.ArogyaPolicy.service.PolicyService;
import com.g1springboard.ArogyaPolicy.service.SchemeService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/user")
public class MyUserController {
    
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private MyUserService userService;

    @Autowired 
    private ClaimService claimService;

    @Autowired
    private SchemeService schemeService;

    @Autowired 
    private PolicyService policyService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/register")
    public String registerpage(Model model) {
        model.addAttribute("myUser", new MyUser()); 
        return "register"; // This will render register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute MyUser myUser) {
        userService.registerUser(myUser);
        return "redirect:/user/login"; // Redirect to login page after successful registration
    }
    @GetMapping("/update")
    public String showUpdateForm(Model model, Principal principal) {
        String username = principal.getName();
        MyUser user = userService.getMyUserByEmail(username).get();
        
        model.addAttribute("updateUser", user);
        return "update-user"; // Thymeleaf template for update form
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute MyUser updatedUser, Principal principal) {
        String username = principal.getName();
        MyUser existingUser = userService.getMyUserByEmail(username).orElseThrow(() -> 
            new RuntimeException("User not found"));
        
        updatedUser.setId(existingUser.getId()); // Ensure the same ID
        userService.updateMyuser(existingUser.getId(), updatedUser);
        return "redirect:/dashboard"; // Redirect after successful update
    }

    @GetMapping("/deactivate-confirmation")
    public String showDeactivateConfirmationPage() {
        return "deactivate-confirmation"; // Return the confirmation page
    }

    @PostMapping("/deactivate")
    public String deactivateUser(RedirectAttributes redirectAttributes) {
        //Or Use Principal principal
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        userService.deactivateUser(email);
        redirectAttributes.addFlashAttribute("message", "Your account has been deactivated successfully.");
        return "redirect:/logout"; // Redirect to logout after deactivation
    }

    @GetMapping("/my-policies")
    public String viewAppliedPolicies(Principal principal, Model model) {
        String loggedInCustomer = principal.getName();
       System.out.println(loggedInCustomer);
        if (loggedInCustomer == null) {
            model.addAttribute("error", "User not authenticated!");
            return "redirect:/login";
        }
        // Fetch the user using email
        Optional<MyUser> userOpt = userService.getMyUserByEmail(loggedInCustomer);
        MyUser user = userOpt.get();
        
        // Fetch the policies applied by the customer
        List<Policy> appliedPolicies = policyService.getPoliciesByUserId(user.getId());
        System.out.println(appliedPolicies);
        if (appliedPolicies == null || appliedPolicies.isEmpty()) {
            model.addAttribute("error", "No policies applied yet.");
        }
    
        model.addAttribute("policies", appliedPolicies);
        return "my-policies"; // Return to the "my-policies" view
    }
    @GetMapping("/policy")
    public String getAllPolicies(Model model,Principal principal) {
        if (principal != null) {
            // User is logged in, retrieve their name
            String username = principal.getName();
            model.addAttribute("user", userService.getMyUserByEmail(username).orElseThrow(() -> new RuntimeException("User not found")).getName());
        } else {
            // User is not logged in
            model.addAttribute("user", "Guest");
        }
        List<Policy> policies = policyService.allPolicies();
        model.addAttribute("policies", policies);
        return "policy-all";
    }
    @GetMapping("/schemes")
    public String viewSchemes(Model model) {
        List<Scheme> schemes = schemeService.getAllScheme();
        model.addAttribute("schemes", schemes);
        return "schemes";
    }

    @GetMapping("/schemes/view/{id}")
    public String viewSchemeDetails(@PathVariable Long id, Model model) {
    Scheme scheme = schemeService.getSchemeDetails(id);
    List<Feedback> feedback = feedbackService.getFeedbackByScheme(id);
    model.addAttribute("scheme", scheme);
    model.addAttribute("feedback", feedback);
    return "view-scheme-user"; // HTML file for detailed scheme view
}


    @GetMapping("/claims")
    public String viewUserClaims(Model model, Principal principal) {

    Optional<MyUser> currentUser = userService.getMyUserByEmail(principal.getName());
    System.out.println("Current"+currentUser.get().getName());

    // Check if currentUser is present and fetch claims
    if (currentUser.isPresent()) {
        List<Claim> claims = claimService.getClaimsOfUser(currentUser.get().getId());
        
        // If claims are null, initialize as an empty list to avoid null pointer exceptions in Thymeleaf
        if (claims == null) {
            claims = new ArrayList<>();
        }
        
        model.addAttribute("claims", claims);

        return "user-claims"; // The view where you want to display the claims
    } else {
        model.addAttribute("claims", new ArrayList<>());
        return "user-claims"; // Handle case when user is not found
    }
}
@GetMapping("/policy/view/{id}")
public String viewPolicy(@PathVariable Long id, Model model) {
    // Fetch the policy details from the database
    Policy policy = policyService.getPolicyDetails(id);
    List<Feedback> feedback = feedbackService.getFeedbackByPolicy(id);

    // Add the policy details to the model
    model.addAttribute("policy", policy);
    model.addAttribute("feedback", feedback);
    // Return the view page
    return "view-policy-user";
}

    @GetMapping("/policy/enroll/{policyId}")
    public String enrollPolicy(@PathVariable Long policyId, Model model, Principal principal) {
    Policy policy = policyService.getPolicyDetails(policyId);
    
    MyUser user = userService.getMyUserByEmail(principal.getName()).get();

    model.addAttribute("policy", policy);
    model.addAttribute("user", user);
    return "policy-confirmation";
}

@PostMapping("/policy/enroll/confirm/{id}")
public String enrollPolicy(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
    String useremail = principal.getName();
    if (useremail == null) {
        redirectAttributes.addFlashAttribute("error", "User not authenticated!");
        return "redirect:/login";
    }

    Optional<MyUser> userOpt = userService.getMyUserByEmail(useremail);
    if (userOpt.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "User not found!");
        return "redirect:/login";
    }

    Policy policy = policyService.getPolicyDetails(id);
    if (policy == null) {
        redirectAttributes.addFlashAttribute("error", "Policy not found!");
        return "redirect:/user/policy";
    }

    // Assign user and update status
    policy.setUser(userOpt.get());
    policy.setEnrollmentStatus(EnrollmentStatus.PENDING);
    policyService.createPolicy(policy);

    redirectAttributes.addFlashAttribute("success", "Enrollment request submitted successfully.");
    return "redirect:/user/my-policies";
}




}
