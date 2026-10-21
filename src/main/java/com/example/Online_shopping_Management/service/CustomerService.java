package com.example.Online_shopping_Management.service;

import com.example.Online_shopping_Management.entity.Customer;
import com.example.Online_shopping_Management.repository.CourierRepository;
import com.example.Online_shopping_Management.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CourierRepository courierRepository;

    public CustomerService(CustomerRepository customerRepository,
                           CourierRepository courierRepository) {
        this.customerRepository = customerRepository;
        this.courierRepository = courierRepository;
    }

    public String save(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> update(UUID id, Customer updated) {
        Optional<Customer> existing = customerRepository.findById(id);
        if (existing.isPresent()) {
            Customer customer = existing.get();
            customer.setFirstName(updated.getFirstName());
            customer.setLastName(updated.getLastName());
            customer.setDistrict(updated.getDistrict());
            customer.setCustomerCode(updated.getCustomerCode());
            customer.setEmail(updated.getEmail());
            customerRepository.save(customer);
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Customer> findByDistrict(String district) {
        return customerRepository.findByDistrictIgnoreCaseOrderByLastNameAsc(district);
    }

    public Object findCustomersOfCourier(UUID courierId) {
        if (!courierRepository.existsById(courierId)) {
            return "The courier with that id does not exist";
        }
        return customerRepository.findCustomersOfCourier(courierId);
    }
}
