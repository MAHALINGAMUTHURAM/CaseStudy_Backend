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
        try {
            if (reviewService.findReview(review)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"ADDFAILS\", \"message\": \"Review already exists\"}"
                );
            }

            reviewService.addReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "{\"code\": \"POSTSUCCESS\", \"message\": \"Review added successfully\"}");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"ADDFAILS\", \"message\": \"Review already exists\"}"
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        try {
            List<Review> reviews = reviewService.getAllReviews();
            if (reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"GETALLFAILS\", \"message\": \"Review list is empty\"}"
                );
            }
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching reviews\"}"
            );
        }
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long review_id) {
        try {
            if (!reviewService.findById(review_id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"GETFAILS\", \"message\": \"Review doesn't exist\"}"
                );
            }
            return ResponseEntity.ok(reviewService.getReviewById(review_id));
        } catch (Exception e) {
            System.out.println(e);
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"GETALLFAILS\", \"message\": \"Review list is empty\"}"
                );
            }
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            System.out.println(e);
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"GETFAILS\", \"message\": \"Review doesn't exist\"}"
                );
            }
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching recent reviews\"}"
            );
        }
    }

    @PutMapping("/update/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable Long review_id, @RequestBody Review review) {
        try {
            if (!reviewService.findById(review_id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"UPDTFAILS\", \"message\": \"Review doesn't exist\"}"
                );
            }
            reviewService.updateReview(review_id, review);
            return ResponseEntity.ok(
                    "{\"code\": \"UPDATESUCCESS\", \"message\": \"Review updated successfully\"}"
            );
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"UPDTFAILS\", \"message\": \"Error updating review\"}"
            );
        }
    }

    @DeleteMapping("/delete/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long review_id) {
        try {
            if (!reviewService.findById(review_id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"DLTFAILS\", \"message\": \"Review doesn't exist\"}"
                );
            }
            reviewService.deleteReview(review_id);
            return ResponseEntity.ok(
                    "{\"code\": \"DELETESUCCESS\", \"message\": \"Review deleted successfully\"}"
            );
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"DLTFAILS\", \"message\": \"Error deleting review\"}"
            );
        }
    }
}
