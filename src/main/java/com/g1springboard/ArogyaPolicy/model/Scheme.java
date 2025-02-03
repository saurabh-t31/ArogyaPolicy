package com.g1springboard.ArogyaPolicy.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scheme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schemeId;
    
    private String schemeName;
    private String description;
    private String eligibilityCriteria;
    private String benefits;
    private String termsConditions;
    private boolean isActive = true;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @OneToMany(mappedBy = "scheme")
    private List<Policy> policies;

    @OneToMany(mappedBy = "scheme")
    private List<Feedback> feedbacks;

}
