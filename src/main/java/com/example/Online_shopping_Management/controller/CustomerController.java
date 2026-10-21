package com.example.Online_shopping_Management.controller;

import com.example.Online_shopping_Management.entity.Customer;
import com.example.Online_shopping_Management.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String message = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Customer> result = customerService.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Customer customer) {
        Optional<Customer> result = customerService.update(id, customer);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        boolean deleted = customerService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Customer deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
    }

    @GetMapping("/by-district")
    public ResponseEntity<List<Customer>> findByDistrict(@RequestParam String district) {
        return ResponseEntity.ok(customerService.findByDistrict(district));
    }

    @GetMapping("/of-courier/{courierId}")
    public ResponseEntity<Object> findCustomersOfCourier(@PathVariable UUID courierId) {
        Object result = customerService.findCustomersOfCourier(courierId);
        if (result instanceof String) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
