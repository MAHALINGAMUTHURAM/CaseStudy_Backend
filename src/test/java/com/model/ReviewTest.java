package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review();
    }

    @Test
    void testGetAndSetReviewId() {
        long expectedId = 1L;
        review.setReview_id(expectedId);
        assertEquals(expectedId, review.getReview_id());
    }

    @Test
    void testGetAndSetReservation() {
        Reservation reservation = new Reservation(); // Assuming you have a Reservation class
        review.setReservation(reservation);
        assertEquals(reservation, review.getReservation());
    }

    @Test
    void testGetAndSetRating() {
        int expectedRating = 5;
        review.setRating(expectedRating);
        assertEquals(expectedRating, review.getRating());
    }

    @Test
    void testGetAndSetComment() {
        String expectedComment = "Great service!";
        review.setComment(expectedComment);
        assertEquals(expectedComment, review.getComment());
    }

    @Test
    void testGetAndSetReviewDate() {
        Date expectedDate = Date.valueOf("2024-01-01");
        review.setReview_date(expectedDate);
        assertEquals(expectedDate, review.getReview_date());
    }
}
