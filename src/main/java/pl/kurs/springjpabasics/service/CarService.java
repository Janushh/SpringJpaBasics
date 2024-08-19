package pl.kurs.springjpabasics.service;

import pl.kurs.springjpabasics.command.CarCommand;
import pl.kurs.springjpabasics.command.UpdateCarCommand;
import pl.kurs.springjpabasics.dto.CarDTO;

import java.util.List;

public interface CarService {
    void addCar(CarCommand command);
    void removeCar(int id);
    List<CarDTO> getAllCars();
    CarDTO updateCar(UpdateCarCommand carCommand);
}
