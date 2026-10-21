package com.example.Online_shopping_Management.repository;

import com.example.Online_shopping_Management.entity.CustomerOrder;
import com.example.Online_shopping_Management.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    boolean existsByCustomerOrder(CustomerOrder customerOrder);

    @Query("SELECT r FROM Review r WHERE r.customerOrder.customer.customerCode = :customerCode ORDER BY r.reviewDate DESC")
    List<Review> findByCustomerCode(@Param("customerCode") String customerCode);
}
