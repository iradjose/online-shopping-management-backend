package com.example.Online_shopping_Management.controller;

import com.example.Online_shopping_Management.entity.DeliveryZone;
import com.example.Online_shopping_Management.service.DeliveryZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-zones")
public class DeliveryZoneController {

    private final DeliveryZoneService deliveryZoneService;

    public DeliveryZoneController(DeliveryZoneService deliveryZoneService) {
        this.deliveryZoneService = deliveryZoneService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody DeliveryZone deliveryZone) {
        String message = deliveryZoneService.save(deliveryZone);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryZone>> findAll() {
        return ResponseEntity.ok(deliveryZoneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<DeliveryZone> result = deliveryZoneService.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The delivery zone with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody DeliveryZone deliveryZone) {
        Optional<DeliveryZone> result = deliveryZoneService.update(id, deliveryZone);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The delivery zone with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        boolean deleted = deliveryZoneService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Delivery zone deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The delivery zone with that id does not exist");
    }

    @GetMapping("/unused")
    public ResponseEntity<List<DeliveryZone>> findUnused() {
        return ResponseEntity.ok(deliveryZoneService.findUnused());
    }
}
