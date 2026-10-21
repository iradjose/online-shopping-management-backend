package com.example.Online_shopping_Management.controller;

import com.example.Online_shopping_Management.entity.Motorbike;
import com.example.Online_shopping_Management.service.MotorbikeService;
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
@RequestMapping("/api/motorbikes")
public class MotorbikeController {

    private final MotorbikeService motorbikeService;

    public MotorbikeController(MotorbikeService motorbikeService) {
        this.motorbikeService = motorbikeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Motorbike motorbike) {
        String message = motorbikeService.save(motorbike);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Motorbike>> findAll() {
        return ResponseEntity.ok(motorbikeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Motorbike> result = motorbikeService.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The motorbike with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Motorbike motorbike) {
        Optional<Motorbike> result = motorbikeService.update(id, motorbike);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The motorbike with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        boolean deleted = motorbikeService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Motorbike deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The motorbike with that id does not exist");
    }

    @GetMapping("/free")
    public ResponseEntity<List<Motorbike>> findFreeMotorbikes() {
        return ResponseEntity.ok(motorbikeService.findFreeMotorbikes());
    }
}
