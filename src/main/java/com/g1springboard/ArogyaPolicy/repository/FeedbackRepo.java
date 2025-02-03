package com.g1springboard.ArogyaPolicy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g1springboard.ArogyaPolicy.model.Feedback;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback , Long> {
    List<Feedback> findByPolicyId(Long policyId);
    List<Feedback> findByScheme_SchemeId(Long schemeId);
    boolean existsByPolicyIdAndUserId(Long policyId, Long userId);
}
