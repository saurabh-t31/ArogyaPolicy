package com.g1springboard.ArogyaPolicy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.service.SchemeService;

@RestController
@RequestMapping("/scheme")
public class SchemeController {

    private SchemeService schemeService;
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")  
    public Scheme createScheme(@RequestBody Scheme newScheme) {
        return schemeService.createScheme(newScheme);
    }
       

}
