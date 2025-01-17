package com.g1springboard.ArogyaPolicy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.service.SchemeService;

@RestController
@RequestMapping("/scheme")
public class SchemeController {
    
    @Autowired
    private SchemeService schemeService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")  
    public Scheme createScheme(@RequestBody Scheme newScheme) {
        return schemeService.createScheme(newScheme);
    }

    @PutMapping("/close/{id}")
    @PreAuthorize("hasRole('ADMIN')")  
    public String closePolicy(@PathVariable Long id){
        schemeService.closeScheme(id);
        return "Scheme Closed";
    }

    @GetMapping("/getScheme/{id}")
    @PreAuthorize("hasRole('ADMIN')")  
    public Scheme getSchemeDetails(@PathVariable Long id){
        return schemeService.getSchemeDetails(id);
    }
       

}
