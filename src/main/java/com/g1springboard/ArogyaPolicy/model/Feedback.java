package com.g1springboard.ArogyaPolicy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    private String comments;
    private int rating;
        
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne 
    @JoinColumn(name="policy_id")
    private Policy policy;
    
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    private boolean acknowledgment;

}
