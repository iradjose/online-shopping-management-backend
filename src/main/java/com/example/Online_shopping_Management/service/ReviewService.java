package com.example.Online_shopping_Management.service;

import com.example.Online_shopping_Management.entity.CustomerOrder;
import com.example.Online_shopping_Management.entity.OrderStatus;
import com.example.Online_shopping_Management.entity.Review;
import com.example.Online_shopping_Management.repository.CustomerOrderRepository;
import com.example.Online_shopping_Management.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         CustomerOrderRepository customerOrderRepository) {
        this.reviewRepository = reviewRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    public String save(UUID customerOrderId, Review review) {
        Optional<CustomerOrder> orderOpt = customerOrderRepository.findById(customerOrderId);
        if (orderOpt.isEmpty()) {
            return "The customer order with that id does not exist";
        }
        CustomerOrder order = orderOpt.get();
        if (order.getStatus() != OrderStatus.DELIVERED) {
            return "Reviews are only for DELIVERED customer orders";
        }
        review.setReviewDate(LocalDate.now());
        review.setCustomerOrder(order);
        reviewRepository.save(review);
        return "Review saved successfully";
    }

    public List<Review> findByCustomerCode(String customerCode) {
        return reviewRepository.findByCustomerCode(customerCode);
    }

    public String delete(UUID id) {
        if (!reviewRepository.existsById(id)) {
            return "The review with that id does not exist";
        }
        reviewRepository.deleteById(id);
        return "Review deleted successfully";
    }
}
