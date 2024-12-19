package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Review;
import com.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/post")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        if (reviewService.findReview(review)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            		"{\"code\": \"ADDFAILS\", \"message\": \"Review already exists\"}"
            );
        }

        reviewService.addReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(
        		"{\"code\": \"POSTSUCCESS\", \"message\": \"Review added successfully\"}");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
    	
        List<Review> reviews = reviewService.getAllReviews();
        if (reviews.isEmpty()) {
            return ResponseEntity.status(400).body("{\"code\": \"GETALLFAILS\", \"message\": \"Review list is empty\"}");
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<?> getReviewById(@PathVariable long review_id) {
        if (!reviewService.findById(review_id)) {
            return ResponseEntity.status(400).body("{\"code\": \"GETFAILS\", \"message\": \"Review doesn't exist\"}");
        }
        return ResponseEntity.ok(reviewService.getReviewById(review_id));
    }

    @GetMapping("/reviews/rating/{rating}")
    public ResponseEntity<?> getReviewsByRating(@PathVariable int rating) {
        List<Review> reviews = reviewService.getReviewsByRating(rating);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(400).body("{\"code\": \"GETALLFAILS\", \"message\": \"Review list is empty\"}");
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/recent")
    public ResponseEntity<?> getRecentReviews() {
    	
        List<Review> reviews = reviewService.getRecentReviews();
        if (reviews.isEmpty()) {
            return ResponseEntity.status(400).body(
            		"{\"code\": \"GETFAILS\", \"message\": \"Review doesn't exist\"}"
            );
        }
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/update/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable long review_id, @RequestBody Review review) {
    	
        if (!reviewService.findById(review_id)) {
            return ResponseEntity.status(400).body(
            		"{\"code\": \"UPDTFAILS\", \"message\": \"Review doesn't exist\"}"
            );
        }
        reviewService.updateReview(review_id, review);
        return ResponseEntity.ok(
        		"{\"code\": \"UPDATESUCCESS\", \"message\": \"Review updated successfully\"}"
        );
    }

    @DeleteMapping("/delete/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable long review_id) {
    	
        if (!reviewService.findById(review_id)) {
            return ResponseEntity.status(400).body(
            		"{\"code\": \"DLTFAILS\", \"message\": \"Review doesn't exist\"}"
            );
        }
        reviewService.deleteReview(review_id);
        return ResponseEntity.ok(
        		"{\"code\": \"DELETESUCCESS\", \"message\": \"Review deleted successfully\"}"
        );
    }
}
