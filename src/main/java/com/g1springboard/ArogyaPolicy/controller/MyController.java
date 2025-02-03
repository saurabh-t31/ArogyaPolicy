package com.g1springboard.ArogyaPolicy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g1springboard.ArogyaPolicy.service.MyUserService;



@Controller
public class MyController {
    
  @Autowired
  MyUserService myUserService;
  
     @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/aboutus")
    public String aboutus(){
        return "about-us";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            // User is logged in, retrieve their name
            String username = principal.getName();
            model.addAttribute("user", myUserService.getMyUserByEmail(username).orElseThrow(() -> new RuntimeException("User not found")).getName());
        } else {
            // User is not logged in
            model.addAttribute("user", "Guest");
        }
        return "dashboard";
    }
    
    

}
