package com.controller;

import com.exception.CustomException;
import com.exception.Response;
import com.model.Review;
import com.model.Reservation;
import com.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Review createMockReview(Long reservationId) {
        Review review = new Review();
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        review.setReservation(reservation);
        review.setRating(5);
        review.setComment("Great experience!");
        review.setReview_date(new java.sql.Date(new Date().getTime())); // Fixed review date
        return review;
    }

    @Test
    void testAddReviewSuccess() throws CustomException {
        Review review = createMockReview(1L);
        when(reviewService.findByReservation(1L)).thenReturn(List.of());
        doNothing().when(reviewService).saveReview(review);

        ResponseEntity<?> response = reviewController.addReview(review);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Review added successfully", ((Response) response.getBody()).getMessage());
        verify(reviewService, times(1)).saveReview(review);
    }

    @Test
    void testAddReviewFailure() {
        Review review = createMockReview(1L);
        when(reviewService.findByReservation(1L)).thenReturn(List.of(review));

        CustomException exception = assertThrows(CustomException.class,
                () -> reviewController.addReview(review));

        assertEquals("ADDFAILS", exception.getCode());
        assertEquals("Review already exists", exception.getMessage());
        verify(reviewService, times(0)).saveReview(any());
    }

    @Test
    void testGetAllReviewsSuccess() throws CustomException {
        Review review1 = createMockReview(1L);
        Review review2 = createMockReview(2L);
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2));

        ResponseEntity<?> response = reviewController.getAllReviews();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, ((List<Review>) response.getBody()).size());
        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    void testGetAllReviewsFailure() {
        when(reviewService.getAllReviews()).thenReturn(List.of());

        CustomException exception = assertThrows(CustomException.class,
                () -> reviewController.getAllReviews());

        assertEquals("GETALLFAILS", exception.getCode());
        assertEquals("Review list is empty", exception.getMessage());
        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    void testGetReviewByIdSuccess() throws CustomException {
        Review review = createMockReview(1L);
        when(reviewService.existsById(1L)).thenReturn(true);
        when(reviewService.getReviewById(1L)).thenReturn(review);

        ResponseEntity<?> response = reviewController.getReviewById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(review, response.getBody());
        verify(reviewService, times(1)).getReviewById(1L);
    }

    @Test
    void testGetReviewByIdFailure() {
        when(reviewService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class,
                () -> reviewController.getReviewById(1L));

        assertEquals("GETFAILS", exception.getCode());
        assertEquals("Review doesn't exist", exception.getMessage());
        verify(reviewService, times(1)).existsById(1L);
    }

    @Test
    void testDeleteReviewSuccess() throws CustomException {
        when(reviewService.existsById(1L)).thenReturn(true);
        doNothing().when(reviewService).deleteReview(1L);

        ResponseEntity<?> response = reviewController.deleteReview(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review deleted successfully", ((Response) response.getBody()).getMessage());
        verify(reviewService, times(1)).deleteReview(1L);
    }

    @Test
    void testDeleteReviewFailure() {
        when(reviewService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class,
                () -> reviewController.deleteReview(1L));

        assertEquals("DLTFAILS", exception.getCode());
        assertEquals("Review doesn't exist", exception.getMessage());
        verify(reviewService, times(0)).deleteReview(anyLong());
    }
}
