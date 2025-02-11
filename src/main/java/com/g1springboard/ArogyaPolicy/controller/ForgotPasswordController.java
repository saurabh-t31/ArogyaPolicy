package com.g1springboard.ArogyaPolicy.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.g1springboard.ArogyaPolicy.service.EmailService;
import com.g1springboard.ArogyaPolicy.service.MyUserService;

@Controller
@RequestMapping("/forgot")
public class ForgotPasswordController {
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private MyUserService userService;

    private Map<String, String> otpStore = new HashMap<>(); // Temporarily store OTPs for simplicity.

    @GetMapping("/forgotpass")
    public String getForgotPasswordPage(){
        return "forgot-password";
    }


    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam String email, Model model) {
        if (userService.isRegisteredEmail(email)) {
            System.out.println("Email"+email);
            String otp = generateOtp();
            otpStore.put(email, otp);
            emailService.sendOtp(email, otp);
            model.addAttribute("email", email);
            return "verify-otp"; // Redirect to OTP verification page
        } else {
            model.addAttribute("error", "Email not registered.Please Enter Appropriate Email!!");
            return "forgot-password";
        }
    }

    @GetMapping("/verify-otp")
    public String verifyOtpPage(){
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        System.out.println("Email:"+email);
        if (otpStore.containsKey(email) && otpStore.get(email).equals(otp)) {
            otpStore.remove(email); // Remove OTP after successful verification
            model.addAttribute("email", email);
            return "reset-password"; // Redirect to reset password page
        } else {
            model.addAttribute("error", "Invalid OTP.");
            return "verify-otp";
        }
    }

    @GetMapping("/reset-password")
    public String resetPassPage(@RequestParam String email,Model model){
        model.addAttribute("email", email);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, Model model) {
        if (password.equals(confirmPassword)) {
            userService.updatePassword(email, password);
            return "redirect:/login?resetSuccess";
        } else {
            model.addAttribute("error", "Both Passwords do not match.");
            return "reset-password";
        }
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(1000000)); // Ensures 6-digit OTP
    }
    


}
