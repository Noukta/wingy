package com.market.wingy.controller;

import com.market.wingy.model.Review;
import com.market.wingy.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{shopId}/{customerId}")
    public ResponseEntity<Optional<Review>> getReviewByCustomerAndShop(
            @PathVariable ObjectId shopId,
            @PathVariable ObjectId customerId) {
        return ResponseEntity.ok(reviewService.findReviewByCustomerAndShop(shopId, customerId));
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<Page<Review>> getShopReviews(
            @PathVariable ObjectId shopId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.findReviews(shopId, rating, start, end, pageable));
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.save(review));
    }

    @PutMapping
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.save(review));
    }
}