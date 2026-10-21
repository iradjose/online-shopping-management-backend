package com.example.Online_shopping_Management.repository;

import com.example.Online_shopping_Management.entity.Courier;
import com.example.Online_shopping_Management.entity.CustomerOrder;
import com.example.Online_shopping_Management.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {

    List<CustomerOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<CustomerOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    boolean existsByCourierAndOrderDateAndStatusNot(Courier courier, LocalDate orderDate, OrderStatus status);

    @Query("SELECT o.status, COUNT(o) FROM CustomerOrder o GROUP BY o.status")
    List<Object[]> countByStatus();

    @Modifying
    @Query("UPDATE CustomerOrder o SET o.status = com.example.Online_shopping_Management.entity.OrderStatus.WITHDRAWN WHERE o.courier.id = :courierId AND o.orderDate = :date AND o.status <> com.example.Online_shopping_Management.entity.OrderStatus.DELIVERED")
    int cancelDayOrders(@Param("courierId") UUID courierId, @Param("date") LocalDate date);
}
