package com.g1springboard.ArogyaPolicy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;

@PostMapping("/submit")
@PreAuthorize("hasRole('USER')")
public Feedback submitFeedback(@RequestBody Feedback feedback) {
    return feedbackService.submitFeedback(feedback.getUser().getId(), feedback.getScheme().getSchemeId(), feedback.getComments(), feedback.getRating());
}


    @PutMapping("/update/{feedbackId}")
    @PreAuthorize("hasRole('USER')")
    public Feedback updateFeedback(@RequestBody Feedback feedback) {

        return feedbackService.updateFeedback(feedback.getFeedbackId(), feedback.getComments(), feedback.getRating());

    }

    @PutMapping("/status/{feedbackId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Feedback changeFeedbackStatus(@PathVariable Long feedbackId,@RequestBody Map<String, String> requestBody) {
        return feedbackService.updateFeedbackStatus(feedbackId, requestBody.get("status"));
    }
}
