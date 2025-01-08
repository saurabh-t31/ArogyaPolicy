package com.g1springboard.ArogyaPolicy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.repository.MyUserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private MyUserRepo myUserRepo; 

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        MyUser myUser = myUserRepo.findByEmail(email)
               .orElseThrow(() -> new UsernameNotFoundException("user not found"));
               
               if (!myUser.isActive()) {
                throw new UsernameNotFoundException("User is deactivated");
            }
            
               return User.builder()
               .username(myUser.getEmail())
               .password(myUser.getPassword())
               .roles(myUser.getRole().replace("ROLE_", ""))
               .build();
    }
    
}
