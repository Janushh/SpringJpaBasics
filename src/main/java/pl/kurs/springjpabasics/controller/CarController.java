package pl.kurs.springjpabasics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping
    public ResponseEntity<Void> addCar(@RequestBody CarCommand command) {
        carService.addCar(command);
        log.info("Car added successfully");
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
