package com.example.managerobackend.services;

import com.example.managerobackend.models.Review;
import com.example.managerobackend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    public Review createOrUpdateReview(Review review) {
        review.setReviewDate(new Date()); // Set review date to current date when creating or updating
        return reviewRepository.save(review);
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }
}
