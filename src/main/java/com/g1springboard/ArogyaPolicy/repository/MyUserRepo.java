package com.g1springboard.ArogyaPolicy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.g1springboard.ArogyaPolicy.model.MyUser;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser , Long> {
    
    Optional<MyUser> findByEmail(String email);


}
