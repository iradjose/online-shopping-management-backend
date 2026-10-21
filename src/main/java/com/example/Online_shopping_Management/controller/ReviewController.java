package com.example.Online_shopping_Management.controller;

import com.example.Online_shopping_Management.entity.Review;
import com.example.Online_shopping_Management.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID customerOrderId, @RequestBody Review review) {
        String message = reviewService.save(customerOrderId, review);
        if (message.equals("Review saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/customer/{customerCode}")
    public ResponseEntity<List<Review>> findByCustomerCode(@PathVariable String customerCode) {
        return ResponseEntity.ok(reviewService.findByCustomerCode(customerCode));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = reviewService.delete(id);
        if (message.equals("Review deleted successfully")) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
