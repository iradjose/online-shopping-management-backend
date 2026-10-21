package com.example.Online_shopping_Management.service;

import com.example.Online_shopping_Management.entity.Courier;
import com.example.Online_shopping_Management.entity.DeliveryZone;
import com.example.Online_shopping_Management.entity.Motorbike;
import com.example.Online_shopping_Management.repository.CourierRepository;
import com.example.Online_shopping_Management.repository.DeliveryZoneRepository;
import com.example.Online_shopping_Management.repository.MotorbikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourierService {

    private final CourierRepository courierRepository;
    private final DeliveryZoneRepository deliveryZoneRepository;
    private final MotorbikeRepository motorbikeRepository;

    public CourierService(CourierRepository courierRepository,
                          DeliveryZoneRepository deliveryZoneRepository,
                          MotorbikeRepository motorbikeRepository) {
        this.courierRepository = courierRepository;
        this.deliveryZoneRepository = deliveryZoneRepository;
        this.motorbikeRepository = motorbikeRepository;
    }

    public String save(Courier courier) {
        courierRepository.save(courier);
        return "Courier saved successfully";
    }

    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    public Optional<Courier> findById(UUID id) {
        return courierRepository.findById(id);
    }

    public Optional<Courier> update(UUID id, Courier updated) {
        Optional<Courier> existing = courierRepository.findById(id);
        if (existing.isPresent()) {
            Courier courier = existing.get();
            courier.setFirstName(updated.getFirstName());
            courier.setLastName(updated.getLastName());
            courier.setPhone(updated.getPhone());
            courier.setMotorbike(updated.getMotorbike());
            courierRepository.save(courier);
            return Optional.of(courier);
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        if (courierRepository.existsById(id)) {
            courierRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Courier> findByDeliveryZoneName(String name) {
        return courierRepository.findByDeliveryZoneName(name);
    }

    public String addDeliveryZone(UUID courierId, UUID deliveryZoneId) {
        Optional<Courier> courierOpt = courierRepository.findById(courierId);
        if (courierOpt.isEmpty()) {
            return "The courier with that id does not exist";
        }
        Optional<DeliveryZone> zoneOpt = deliveryZoneRepository.findById(deliveryZoneId);
        if (zoneOpt.isEmpty()) {
            return "That delivery zone does not exist";
        }
        Courier courier = courierOpt.get();
        DeliveryZone zone = zoneOpt.get();
        if (courier.getDeliveryZones().contains(zone)) {
            return "The courier already has that delivery zone";
        }
        courier.getDeliveryZones().add(zone);
        courierRepository.save(courier);
        return "Delivery zone added to the courier";
    }

    public String changeMotorbike(UUID courierId, int bikeNumber) {
        Optional<Courier> courierOpt = courierRepository.findById(courierId);
        if (courierOpt.isEmpty()) {
            return "The courier with that id does not exist";
        }
        Optional<Motorbike> bikeOpt = motorbikeRepository.findByBikeNumber(bikeNumber);
        if (bikeOpt.isEmpty()) {
            return "The motorbike with that bikeNumber does not exist";
        }
        Motorbike bike = bikeOpt.get();
        Courier courier = courierOpt.get();
        if (courierRepository.existsByMotorbike(bike) && !bike.getId().equals(courier.getMotorbike() != null ? courier.getMotorbike().getId() : null)) {
            return "That motorbike is taken by another courier";
        }
        courier.setMotorbike(bike);
        courierRepository.save(courier);
        return "Motorbike changed successfully";
    }
}
