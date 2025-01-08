package com.g1springboard.ArogyaPolicy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.repository.MyUserRepo;

@Service
public class MyUserService {
    
    @Autowired
    private  MyUserRepo myUserRepo;

    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

   public MyUser registerUser(MyUser myUser) {
        // Encode password before saving
        if (myUser.getRole() == null || myUser.getRole().isEmpty()) {
            myUser.setRole("ROLE_USER"); // Default role
        }
        myUser.setActive(true);
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUser.setRegisterdate(LocalDate.now());
        return myUserRepo.save(myUser);
    } 

    public Optional<MyUser> getMyUserByEmail(String email){
        return myUserRepo.findByEmail(email);
    }

    public boolean loginUser(MyUser myuser) {
        
        return passwordEncoder.matches(myuser.getPassword(),myUserRepo.findByEmail(myuser.getEmail()).get().getPassword());
  
    }

    public List<MyUser> getallmyusers(){
        return myUserRepo.findAll();
    }

    public MyUser updateMyuser(Long userId , MyUser updatedUser){
        
        Optional<MyUser> existingUser = myUserRepo.findById(userId);
        if (!existingUser.isPresent())  throw new RuntimeException("User not found"); 
        
        if (updatedUser.getName() != null) {
            existingUser.get().setName(updatedUser.getName());
        }  
        if (updatedUser.getAddress() != null) {
            existingUser.get().setAddress(updatedUser.getAddress());
        }
        
        return myUserRepo.save(existingUser.get());
    
    }
    
    public String deactivateUser(String email) {
        MyUser user = myUserRepo.findByEmail(email)
             .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        myUserRepo.save(user);
        return "User Deactivated Succesfully";
    }

}
