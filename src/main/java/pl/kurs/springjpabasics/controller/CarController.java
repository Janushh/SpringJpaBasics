package pl.kurs.springjpabasics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.springjpabasics.command.CarCommand;
import pl.kurs.springjpabasics.command.UpdateCarCommand;
import pl.kurs.springjpabasics.dto.CarDTO;
import pl.kurs.springjpabasics.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@RequestBody CarCommand command) {
        carService.addCar(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<CarDTO> updateCar(@RequestBody UpdateCarCommand command) {
        return ResponseEntity.ok(carService.updateCar(command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.removeCar(id);
        return ResponseEntity.ok().build();
    }
}
