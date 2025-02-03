package com.g1springboard.ArogyaPolicy.service;

import java.util.List;
import java.util.Optional;

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

    public List<Scheme> getAllScheme(){
        return schemeRepo.findAll();
    }

    public Scheme getSchemeDetails(Long schemeId){
        return schemeRepo.findById(schemeId)
        .orElseThrow(() -> new RuntimeException("Scheme not Found"));
    }

    public void closeScheme(Long schemeId){
       Scheme scheme = schemeRepo.findById(schemeId)
                   .orElseThrow(() -> new RuntimeException("Scheme not Found"));

       scheme.setActive(false);
       schemeRepo.save(scheme);
    }

    public Scheme updateScheme(Scheme scheme) {
        Scheme existingScheme = getSchemeDetails(scheme.getSchemeId());
        
        // Update fields
        existingScheme.setSchemeName(scheme.getSchemeName());
        existingScheme.setDescription(scheme.getDescription());
        existingScheme.setEligibilityCriteria(scheme.getEligibilityCriteria());
        existingScheme.setBenefits(scheme.getBenefits());
        existingScheme.setTermsConditions(scheme.getTermsConditions());
        existingScheme.setActive(scheme.isActive());

        // Save updated scheme
        return schemeRepo.save(existingScheme);
    }




    

}
