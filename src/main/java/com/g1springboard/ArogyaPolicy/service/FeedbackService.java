package com.g1springboard.ArogyaPolicy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.MyUser;
import com.g1springboard.ArogyaPolicy.model.Policy;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.repository.FeedbackRepo;
import com.g1springboard.ArogyaPolicy.repository.MyUserRepo;
import com.g1springboard.ArogyaPolicy.repository.SchemeRepo;

@Service
public class FeedbackService {
    
      @Autowired
      private FeedbackRepo feedbackRepo;

      @Autowired
      private PolicyService policyService;

      @Autowired 
      private MyUserService myUserService;
      
      public void saveFeedback(Long policyId, Long userId, Feedback feedback) {
        Policy policy = policyService.getPolicyDetails(policyId);
        MyUser user = myUserService.getMyUserById(userId);
        feedback.setPolicy(policy);
        feedback.setScheme(policy.getScheme());
        feedback.setUser(user);
        feedback.setAcknowledgment(false);
        feedbackRepo.save(feedback);
    }
    public List<Feedback> getFeedbackByPolicy(Long policyId) {
      return feedbackRepo.findByPolicyId(policyId);
  }

  public List<Feedback> getFeedbackByScheme(Long schemeId) {
      return feedbackRepo.findByScheme_SchemeId(schemeId);
  }

  public List<Feedback> getAllFeedbacks(){
    return feedbackRepo.findAll();
  }


  public boolean hasFeedbackForPolicy(Long policyId, Long userId) {
    // Check if feedback exists for the given user and policy
    return feedbackRepo.existsByPolicyIdAndUserId(policyId, userId);
}

    

    }