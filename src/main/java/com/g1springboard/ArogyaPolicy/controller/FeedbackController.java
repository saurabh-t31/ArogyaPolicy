package com.g1springboard.ArogyaPolicy.controller;


import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.service.FeedbackService;
import com.g1springboard.ArogyaPolicy.service.MyUserService;
import com.g1springboard.ArogyaPolicy.service.PolicyService;

@Controller
@RequestMapping("/user/feedback")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private MyUserService userService;

    @Autowired
    private PolicyService policyService;
    
     @GetMapping("/{policyId}")
    public String showFeedbackForm(@PathVariable Long policyId, Model model,Principal principal,RedirectAttributes redirectAttributes) {
    String username = principal.getName();
    MyUser user = userService.getMyUserByEmail(username).get();

    boolean feedbackExists = feedbackService.hasFeedbackForPolicy(policyId, user.getId());

    if (feedbackExists) {
        redirectAttributes.addFlashAttribute("errorMessage", "Feedback already given for this policy.");
        return "redirect:/user/policy/view/" + policyId;
    }
        Policy policy = policyService.getPolicyDetails(policyId);
        model.addAttribute("policy", policy);
        model.addAttribute("scheme", policy.getScheme());
        model.addAttribute("feedback", new Feedback());
        return "feedback-form";
    }

    // Submit Feedback
    @PostMapping("/{policyId}")
    public String submitFeedback(@PathVariable Long policyId,@ModelAttribute Feedback feedback,Principal principal,RedirectAttributes redirectAttributes) {

        String username = principal.getName();
        MyUser user = userService.getMyUserByEmail(username).get();
        feedbackService.saveFeedback(policyId, user.getId() , feedback);
        redirectAttributes.addFlashAttribute("message", "Feedback Submitted Successfully!");

        return "redirect:/user/policy/view/" + policyId;
        }



}
