package com.g1springboard.ArogyaPolicy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.service.MyUserService;
import com.g1springboard.ArogyaPolicy.service.PolicyService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/policy")
public class PolicyController {
    
    @Autowired 
    private PolicyService policyService;

    @Autowired 
    private MyUserService myUserService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Policy createPolicy(@RequestBody Policy policy){
          return policyService.createPolicy(policy);
        }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Policy updatePolicy(@RequestBody Policy policy, @PathVariable Long id){
           return policyService.updatePolicy(id, policy);
    }


    @DeleteMapping("/delete") //HARD DELETE
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePolicy(@PathVariable Long id ){
        policyService.deletePolicy(id);
        return "policy deleted";
    }

    @PutMapping("/close/{id}") //SOFT DELTE
    @PreAuthorize("hasRole('ADMIN')")
    public String closePolicy(@PathVariable Long id){
        policyService.closePolicy(id);
        return "Policy Closed";
    }

    @GetMapping("getPolicy/{id}")
    public Policy policyDetails(@PathVariable Long id) {
        return policyService.getPolicyDetails(id);

    }
    
    @GetMapping("/user/allmypolicies")
    @PreAuthorize("hasRole('USER')")
    public List<Policy> getAllMyPolicies() {
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(); 

        MyUser user = myUserService.getMyUserByEmail(email).get();
        return policyService.getPoliciesByUserId(user.getId());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Policy> getPoliciesByUserId( @PathVariable Long id) {
        return policyService.getPoliciesByUserId(id);
    }

    
    



    
    




    


}
