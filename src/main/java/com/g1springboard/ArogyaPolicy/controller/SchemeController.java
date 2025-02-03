package com.g1springboard.ArogyaPolicy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.g1springboard.ArogyaPolicy.model.Feedback;
import com.g1springboard.ArogyaPolicy.model.Scheme;
import com.g1springboard.ArogyaPolicy.service.FeedbackService;
import com.g1springboard.ArogyaPolicy.service.SchemeService;

@Controller
@RequestMapping("/admin/schemes")
public class SchemeController {
    
    @Autowired
    private SchemeService schemeService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String viewSchemes(Model model) {
        List<Scheme> schemes = schemeService.getAllScheme();
        model.addAttribute("schemes", schemes);
        return "admin-schemes";
    }

    @GetMapping("/view/{id}")
    public String viewScheme(@PathVariable Long id, Model model) {
    Scheme scheme = schemeService.getSchemeDetails(id);
    List<Feedback> feedback = feedbackService.getFeedbackByScheme(id);     // Fetch the scheme by ID
    model.addAttribute("scheme", scheme);
    model.addAttribute("feedback", feedback);
    return "view-schemes"; // This should return the view template above
}
    
    @GetMapping("/create")
     public String createScheme(Model model) {
      model.addAttribute("scheme", new Scheme());
      return "scheme-form";
    }

    
    @PostMapping("/create")
    public String createScheme(@ModelAttribute Scheme scheme) {
    // Logic to save the scheme
    schemeService.createScheme(scheme);
    return "redirect:/admin/schemes"; // Redirect to schemes list after creation
    }

    @GetMapping("/edit/{id}")
    public String editScheme(@PathVariable("id") Long schemeId, Model model) {
    
    Scheme scheme = schemeService.getSchemeDetails(schemeId);
    model.addAttribute("scheme", scheme);
    return "scheme-form";  
  }
    @GetMapping("/close/{id}")
    @PreAuthorize("hasRole('ADMIN')")  
    public String closePolicy(@PathVariable Long id){
        schemeService.closeScheme(id);
        return "redirect:/admin/schemes";
    }

    @GetMapping("/getScheme/{id}")
    @PreAuthorize("hasRole('ADMIN')")  
    public Scheme getSchemeDetails(@PathVariable Long id){
        return schemeService.getSchemeDetails(id);
    }
       

}
