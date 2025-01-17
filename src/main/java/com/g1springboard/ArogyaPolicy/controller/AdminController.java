package com.g1springboard.ArogyaPolicy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.service.MyUserService;


@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MyUserService myUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String getAdminDashboard(){
        return "Welcome to admin Dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getallusers")
    public List<MyUser> getallUsers(){
        return myUserService.getallmyusers();
    }
    
    


}
