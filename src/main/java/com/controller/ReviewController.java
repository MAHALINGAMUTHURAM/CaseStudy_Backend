package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Review;
import com.service.ReviewService;
import com.exception.CustomException;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/post")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        try {
            if (reviewService.findReview(review)) {
                throw new CustomException("ADDFAILS", "Review already exists");
            }

            reviewService.addReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "{\"code\": \"POSTSUCCESS\", \"message\": \"Review added successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"ADDFAILS\", \"message\": \"Error adding review\"}"
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        try {
            List<Review> reviews = reviewService.getAllReviews();
            if (reviews.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Review list is empty");
            }
            return ResponseEntity.ok(reviews);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching reviews\"}"
            );
        }
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long review_id) {
        try {
            if (!reviewService.findById(review_id)) {
                throw new CustomException("GETFAILS", "Review doesn't exist");
            }
            return ResponseEntity.ok(reviewService.getReviewById(review_id));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching review by ID\"}"
            );
        }
    }

    @GetMapping("/reviews/rating/{rating}")
    public ResponseEntity<?> getReviewsByRating(@PathVariable int rating) {
        try {
            List<Review> reviews = reviewService.getReviewsByRating(rating);
            if (reviews.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Review list is empty");
            }
            return ResponseEntity.ok(reviews);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching reviews by rating\"}"
            );
        }
    }

    @GetMapping("/reviews/recent")
    public ResponseEntity<?> getRecentReviews() {
        try {
            List<Review> reviews = reviewService.getRecentReviews();
            if (reviews.isEmpty()) {
                throw new CustomException("GETFAILS", "Review doesn't exist");
            }
            return ResponseEntity.ok(reviews);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching recent reviews\"}"
            );
        }
    }

    @PutMapping("/update/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable Long review_id, @RequestBody Review review) {
        try {
            if (!reviewService.findById(review_id)) {
                throw new CustomException("UPDTFAILS", "Review doesn't exist");
            }
            reviewService.updateReview(review_id, review);
            return ResponseEntity.ok(
                    "{\"code\": \"UPDATESUCCESS\", \"message\": \"Review updated successfully\"}"
            );
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"UPDTFAILS\", \"message\": \"Error updating review\"}"
            );
        }
    }

    @DeleteMapping("/delete/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long review_id) {
        try {
            if (!reviewService.findById(review_id)) {
                throw new CustomException("DLTFAILS", "Review doesn't exist");
            }
            reviewService.deleteReview(review_id);
            return ResponseEntity.ok(
                    "{\"code\": \"DELETESUCCESS\", \"message\": \"Review deleted successfully\"}"
            );
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"DLTFAILS\", \"message\": \"Error deleting review\"}"
            );
        }
    }
}
