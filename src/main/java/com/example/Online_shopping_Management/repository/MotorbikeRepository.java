package com.example.Online_shopping_Management.repository;

import com.example.Online_shopping_Management.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MotorbikeRepository extends JpaRepository<Motorbike, UUID> {

    Optional<Motorbike> findByBikeNumber(int bikeNumber);

    @Query("SELECT m FROM Motorbike m WHERE NOT EXISTS (SELECT c FROM Courier c WHERE c.motorbike = m) ORDER BY m.bikeNumber ASC")
    List<Motorbike> findFreeMotorbikes();
}
