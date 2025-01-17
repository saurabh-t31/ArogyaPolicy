package com.g1springboard.ArogyaPolicy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.service.MyUserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class MyUserController {
    
    @Autowired
    private MyUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody MyUser myUser) {
        
        userService.registerUser(myUser);
        return "Registered Succesfully";
            
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody MyUser myUser){

        Optional<MyUser> userOpt = userService.getMyUserByEmail(myUser.getEmail());

        if (userOpt.isEmpty()) {
            return "User not found!";
        }
    
        MyUser user = userOpt.get();
        if (userService.loginUser(myUser)) {
            return "Login successful! Role: " + user.getRole();
        } else {
            return "Invalid credentials!";
        }
    }    
    
    @PutMapping("/update/{userId}")
    public MyUser updateUser(@PathVariable Long userId , @RequestBody MyUser user) {
        return userService.updateMyuser(userId, user);
    }

    @PutMapping("/deactivate")
    public void deactivatUser(){
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(); 
        userService.deactivateUser(email);
    }
    

}
