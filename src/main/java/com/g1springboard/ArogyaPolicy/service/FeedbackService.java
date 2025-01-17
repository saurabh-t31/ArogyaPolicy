package com.g1springboard.ArogyaPolicy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.repository.FeedbackRepo;
import com.g1springboard.ArogyaPolicy.repository.MyUserRepo;
import com.g1springboard.ArogyaPolicy.repository.SchemeRepo;

@Service
public class FeedbackService {
    
      @Autowired
      private FeedbackRepo feedbackRepo;

      @Autowired
      private MyUserRepo myUserRepo;

      @Autowired 
      private SchemeRepo schemeRepo;


      public List<Feedback> getAllFeedback(){
        return feedbackRepo.findAll();
      }

      public Feedback submitFeedback(Long userId,Long schemeId,String comments , int rating){

               MyUser user = myUserRepo.findById(userId)
               .orElseThrow(()-> new RuntimeException("User not Found"));
               Scheme scheme = schemeRepo.findById(schemeId)
               .orElseThrow(()->new RuntimeException("Scheme not Found"));
               
               Feedback feedback = new Feedback();
               feedback.setComments(comments);
               feedback.setRating(rating);
               feedback.setScheme(scheme);
               feedback.setUser(user);
               feedback.setFeedbackStatus("In Progress");
               feedback.setAcknowledgment(false); // Acknowledgment initially false
                 
               
               return feedbackRepo.save(feedback);
                       
      }
      public Feedback updateFeedback(Long feedbackId, String comments, int rating) {
        Feedback feedback = feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setComments(comments);
        feedback.setRating(rating);

        
        return feedbackRepo.save(feedback);
    }

    // Method to change feedback status by admin
    public Feedback updateFeedbackStatus(Long feedbackId, String status) {
      Feedback feedback = feedbackRepo.findById(feedbackId)
              .orElseThrow(() -> new RuntimeException("Feedback not found"));

      feedback.setFeedbackStatus(status);
      
      return feedbackRepo.save(feedback);
  }

    }