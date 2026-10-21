package com.example.Online_shopping_Management.repository;

import com.example.Online_shopping_Management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByDistrictIgnoreCaseOrderByLastNameAsc(String district);

    Optional<Customer> findByCustomerCode(String customerCode);

    @Query("SELECT DISTINCT c FROM Customer c JOIN CustomerOrder o ON o.customer = c WHERE o.courier.id = :courierId")
    List<Customer> findCustomersOfCourier(@Param("courierId") UUID courierId);
}
