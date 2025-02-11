package com.g1springboard.ArogyaPolicy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
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
        if(!myUserRepo.findByEmail(myUser.getEmail()).isPresent()){
        myUser.setEmail(myUser.getEmail());
        }
        
        myUser.setActive(true);
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUser.setRegisterdate(LocalDate.now());
        return myUserRepo.save(myUser);
    } 

    public Optional<MyUser> getMyUserByEmail(String email){
        return myUserRepo.findByEmail(email);
    }

    public MyUser getMyUserById(Long id) {
        return myUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }


    public boolean loginUser(MyUser myuser) {
        
        return passwordEncoder.matches(myuser.getPassword(),myUserRepo.findByEmail(myuser.getEmail()).get().getPassword());
  
    }

    public List<MyUser> getallmyusers(){
        return myUserRepo.findAll();
    }

    public MyUser updateMyuser(Long userId, MyUser updatedUser) {
        // Fetch the existing user or throw an exception if not found
        MyUser existingUser = myUserRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // Update mutable fields if they are not null
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getGender()!= null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getDob()!=null) {
            existingUser.setDob(updatedUser.getDob());
        }
        if (updatedUser.getAddress() != null) {
            existingUser.setAddress(updatedUser.getAddress());
        }
    
        // Save and return the updated user
        return myUserRepo.save(existingUser);
    }
    
    
    public String deactivateUser(String email) {
        MyUser user = myUserRepo.findByEmail(email)
             .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        myUserRepo.save(user);
        return "User Deactivated Succesfully";
    }

    public boolean isRegisteredEmail(String email) {
        return myUserRepo.findByEmail(email).isPresent();
    }

    public void updatePassword(String email, String password) {
        Optional<MyUser> user = myUserRepo.findByEmail(email);
        if (user.isPresent()) {
            MyUser updatedUser = user.get();
            updatedUser.setPassword(passwordEncoder.encode(password)); // Ensure to hash password if required
            myUserRepo.save(updatedUser);
        }

}
}
