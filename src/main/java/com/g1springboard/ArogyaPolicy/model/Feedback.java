package com.g1springboard.ArogyaPolicy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    private String comments;
    private int rating;
    private String feedbackStatus;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;
    
    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

}
