package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Review;
import com.example.managerobackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") String id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review createdReview = reviewService.createOrUpdateReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") String id,
                                               @RequestBody Review review) {
        review.setId(id);
        Review updatedReview = reviewService.createOrUpdateReview(review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") String id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
