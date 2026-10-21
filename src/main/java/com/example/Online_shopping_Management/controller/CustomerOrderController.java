package com.example.Online_shopping_Management.controller;

import com.example.Online_shopping_Management.entity.CustomerOrder;
import com.example.Online_shopping_Management.entity.OrderStatus;
import com.example.Online_shopping_Management.service.CustomerOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer-orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CustomerOrder order) {
        String message = customerOrderService.save(order);
        if (message.equals("Customer order saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        if (message.contains("in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerOrder>> findAll() {
        return ResponseEntity.ok(customerOrderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<CustomerOrder> result = customerOrderService.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer order with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody CustomerOrder order) {
        Optional<CustomerOrder> result = customerOrderService.update(id, order);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer order with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = customerOrderService.delete(id);
        if (message.equals("Customer order deleted successfully")) {
            return ResponseEntity.ok(message);
        }
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        String message = customerOrderService.updateStatus(id, status);
        if (message.equals("Customer order status updated successfully")) {
            return ResponseEntity.ok(message);
        }
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<CustomerOrder>> findByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(customerOrderService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<CustomerOrder>> findBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(customerOrderService.findBetweenDates(startDate, endDate));
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> statsByStatus() {
        return ResponseEntity.ok(customerOrderService.countByStatus());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID courierId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String message = customerOrderService.cancelDay(courierId, localDate);
        return ResponseEntity.ok(message);
    }
}
