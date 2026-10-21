package com.example.Online_shopping_Management.service;

import com.example.Online_shopping_Management.entity.Motorbike;
import com.example.Online_shopping_Management.repository.MotorbikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MotorbikeService {

    private final MotorbikeRepository motorbikeRepository;

    public MotorbikeService(MotorbikeRepository motorbikeRepository) {
        this.motorbikeRepository = motorbikeRepository;
    }

    public String save(Motorbike motorbike) {
        motorbikeRepository.save(motorbike);
        return "Motorbike saved successfully";
    }

    public List<Motorbike> findAll() {
        return motorbikeRepository.findAll();
    }

    public Optional<Motorbike> findById(UUID id) {
        return motorbikeRepository.findById(id);
    }

    public Optional<Motorbike> update(UUID id, Motorbike updated) {
        Optional<Motorbike> existing = motorbikeRepository.findById(id);
        if (existing.isPresent()) {
            Motorbike bike = existing.get();
            bike.setBikeNumber(updated.getBikeNumber());
            bike.setName(updated.getName());
            motorbikeRepository.save(bike);
            return Optional.of(bike);
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        if (motorbikeRepository.existsById(id)) {
            motorbikeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Motorbike> findFreeMotorbikes() {
        return motorbikeRepository.findFreeMotorbikes();
    }

    public Optional<Motorbike> findByBikeNumber(int bikeNumber) {
        return motorbikeRepository.findByBikeNumber(bikeNumber);
    }
}
