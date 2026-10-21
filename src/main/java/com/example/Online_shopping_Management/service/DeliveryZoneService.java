package com.example.Online_shopping_Management.service;

import com.example.Online_shopping_Management.entity.DeliveryZone;
import com.example.Online_shopping_Management.repository.DeliveryZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryZoneService {

    private final DeliveryZoneRepository deliveryZoneRepository;

    public DeliveryZoneService(DeliveryZoneRepository deliveryZoneRepository) {
        this.deliveryZoneRepository = deliveryZoneRepository;
    }

    public String save(DeliveryZone deliveryZone) {
        deliveryZoneRepository.save(deliveryZone);
        return "Delivery zone saved successfully";
    }

    public List<DeliveryZone> findAll() {
        return deliveryZoneRepository.findAll();
    }

    public Optional<DeliveryZone> findById(UUID id) {
        return deliveryZoneRepository.findById(id);
    }

    public Optional<DeliveryZone> update(UUID id, DeliveryZone updated) {
        Optional<DeliveryZone> existing = deliveryZoneRepository.findById(id);
        if (existing.isPresent()) {
            DeliveryZone zone = existing.get();
            zone.setName(updated.getName());
            deliveryZoneRepository.save(zone);
            return Optional.of(zone);
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        if (deliveryZoneRepository.existsById(id)) {
            deliveryZoneRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DeliveryZone> findUnused() {
        return deliveryZoneRepository.findUnused();
    }
}
