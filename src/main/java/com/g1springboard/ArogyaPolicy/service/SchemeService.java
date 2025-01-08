package com.g1springboard.ArogyaPolicy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.repository.SchemeRepo;

@Service
public class SchemeService {
    
    @Autowired
    private SchemeRepo schemeRepo;

    public Scheme createScheme(Scheme scheme){
            return schemeRepo.save(scheme);
    }
    

}
