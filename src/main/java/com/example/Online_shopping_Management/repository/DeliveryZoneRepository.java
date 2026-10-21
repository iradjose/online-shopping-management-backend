package com.example.Online_shopping_Management.repository;

import com.example.Online_shopping_Management.entity.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, UUID> {

    @Query("SELECT d FROM DeliveryZone d WHERE d.couriers IS EMPTY")
    List<DeliveryZone> findUnused();
}
