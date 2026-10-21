package com.example.Online_shopping_Management.repository;

import com.example.Online_shopping_Management.entity.Courier;
import com.example.Online_shopping_Management.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

    boolean existsByMotorbike(Motorbike motorbike);

    @Query("SELECT c FROM Courier c JOIN c.deliveryZones d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Courier> findByDeliveryZoneName(@Param("name") String name);
}
