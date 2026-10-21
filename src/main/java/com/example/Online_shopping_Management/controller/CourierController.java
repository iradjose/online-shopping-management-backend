package com.example.Online_shopping_Management.controller;

import com.example.Online_shopping_Management.entity.Courier;
import com.example.Online_shopping_Management.service.CourierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Courier courier) {
        String message = courierService.save(courier);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Courier>> findAll() {
        return ResponseEntity.ok(courierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Courier> result = courierService.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The courier with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Courier courier) {
        Optional<Courier> result = courierService.update(id, courier);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The courier with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        boolean deleted = courierService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Courier deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The courier with that id does not exist");
    }

    @GetMapping("/by-delivery-zone")
    public ResponseEntity<List<Courier>> findByDeliveryZone(@RequestParam String name) {
        return ResponseEntity.ok(courierService.findByDeliveryZoneName(name));
    }

    @PutMapping("/{courierId}/delivery-zones/{deliveryZoneId}")
    public ResponseEntity<String> addDeliveryZone(@PathVariable UUID courierId, @PathVariable UUID deliveryZoneId) {
        String message = courierService.addDeliveryZone(courierId, deliveryZoneId);
        if (message.equals("Delivery zone added to the courier")) {
            return ResponseEntity.ok(message);
        }
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @PatchMapping("/{courierId}/motorbike")
    public ResponseEntity<String> changeMotorbike(@PathVariable UUID courierId, @RequestParam int bikeNumber) {
        String message = courierService.changeMotorbike(courierId, bikeNumber);
        if (message.equals("Motorbike changed successfully")) {
            return ResponseEntity.ok(message);
        }
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
