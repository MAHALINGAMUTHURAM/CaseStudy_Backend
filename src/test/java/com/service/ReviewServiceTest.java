package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.ReviewDAO;
import com.model.Reservation;
import com.model.Review;
import com.service.ReviewService;

class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewDAO reviewDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        Review review = new Review();
        reviewService.saveReview(review);

        verify(reviewDAO, times(1)).save(review);
    }

    @Test
    void testGetAllReviews() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewDAO.findAll()).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviews();

        assertEquals(2, result.size());
        verify(reviewDAO, times(1)).findAll();
    }

    @Test
    void testUpdateReview() {
        Review existingReview = new Review();
        existingReview.setComment("Old Comment");
        existingReview.setRating(3);

        Review updatedReview = new Review();
        updatedReview.setComment("New Comment");
        updatedReview.setRating(5);

        when(reviewDAO.findById(1L)).thenReturn(Optional.of(existingReview));

        reviewService.updateReview(1L, updatedReview);

        verify(reviewDAO, times(1)).save(existingReview);
        assertEquals("New Comment", existingReview.getComment());
        assertEquals(5, existingReview.getRating());
    }

    @Test
    void testDeleteReview() {
        Review review = new Review();
        when(reviewDAO.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(1L);

        verify(reviewDAO, times(1)).delete(review);
    }

    @Test
    void testExistsById() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.of(new Review()));

        boolean result = reviewService.existsById(1L);

        assertTrue(result);
        verify(reviewDAO, times(1)).findById(1L);
    }

    @Test
    void testGetReviewById() {
        Review review = new Review();
        when(reviewDAO.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.getReviewById(1L);

        assertNotNull(result);
        verify(reviewDAO, times(1)).findById(1L);
    }

    @Test
    void testGetRecentReviews() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewDAO.findRecentReviews()).thenReturn(reviews);

        List<Review> result = reviewService.getRecentReviews();

        assertEquals(2, result.size());
        verify(reviewDAO, times(1)).findRecentReviews();
    }

    @Test
    void testGetReviewsByRating() {
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewDAO.findByRating(5)).thenReturn(reviews);

        List<Review> result = reviewService.getReviewsByRating(5);

        assertEquals(2, result.size());
        verify(reviewDAO, times(1)).findByRating(5);
    }

    @Test
    void testFindByReservation() {
        List<Review> reservations = Arrays.asList(new Review(), new Review());
        when(reviewDAO.findByReservation_ReservationId(1L)).thenReturn(reservations);

        List<Review> result = reviewService.findByReservation(1L);

        assertEquals(2, result.size());
        verify(reviewDAO, times(1)).findByReservation_ReservationId(1L);
    }
}
