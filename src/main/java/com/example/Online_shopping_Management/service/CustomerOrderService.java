package com.example.Online_shopping_Management.service;

import com.example.Online_shopping_Management.entity.CustomerOrder;
import com.example.Online_shopping_Management.entity.OrderStatus;
import com.example.Online_shopping_Management.repository.CustomerOrderRepository;
import com.example.Online_shopping_Management.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ReviewRepository reviewRepository;

    public CustomerOrderService(CustomerOrderRepository customerOrderRepository,
                                ReviewRepository reviewRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.reviewRepository = reviewRepository;
    }

    public String save(CustomerOrder order) {
        if (order.getOrderDate().isBefore(LocalDate.now())) {
            return "Customer order date cannot be in the past";
        }
        if (order.getCourier() != null && customerOrderRepository.existsByCourierAndOrderDateAndStatusNot(
                order.getCourier(), order.getOrderDate(), OrderStatus.WITHDRAWN)) {
            return "Courier is already booked for a delivery on that date";
        }
        order.setStatus(OrderStatus.PLACED);
        customerOrderRepository.save(order);
        return "Customer order saved successfully";
    }

    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    public Optional<CustomerOrder> findById(UUID id) {
        return customerOrderRepository.findById(id);
    }

    public Optional<CustomerOrder> update(UUID id, CustomerOrder updated) {
        Optional<CustomerOrder> existing = customerOrderRepository.findById(id);
        if (existing.isPresent()) {
            CustomerOrder order = existing.get();
            order.setOrderDate(updated.getOrderDate());
            order.setDeliveryAddress(updated.getDeliveryAddress());
            order.setTotalAmount(updated.getTotalAmount());
            order.setCustomer(updated.getCustomer());
            order.setCourier(updated.getCourier());
            customerOrderRepository.save(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        Optional<CustomerOrder> existing = customerOrderRepository.findById(id);
        if (existing.isEmpty()) {
            return "The customer order with that id does not exist";
        }
        if (reviewRepository.existsByCustomerOrder(existing.get())) {
            return "That customer order has reviews, delete them first";
        }
        customerOrderRepository.deleteById(id);
        return "Customer order deleted successfully";
    }

    public String updateStatus(UUID id, OrderStatus newStatus) {
        Optional<CustomerOrder> existing = customerOrderRepository.findById(id);
        if (existing.isEmpty()) {
            return "The customer order with that id does not exist";
        }
        CustomerOrder order = existing.get();
        OrderStatus current = order.getStatus();
        boolean allowed = false;
        if (current == OrderStatus.PLACED && (newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.WITHDRAWN)) {
            allowed = true;
        }
        if (current == OrderStatus.SHIPPED && (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.WITHDRAWN)) {
            allowed = true;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        order.setStatus(newStatus);
        customerOrderRepository.save(order);
        return "Customer order status updated successfully";
    }

    public List<CustomerOrder> findByStatus(OrderStatus status) {
        return customerOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    public List<CustomerOrder> findBetweenDates(LocalDate start, LocalDate end) {
        return customerOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    public List<Object[]> countByStatus() {
        return customerOrderRepository.countByStatus();
    }

    @Transactional
    public String cancelDay(UUID courierId, LocalDate date) {
        int count = customerOrderRepository.cancelDayOrders(courierId, date);
        return count + " customer orders cancelled";
    }
}
