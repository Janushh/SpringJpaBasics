package pl.kurs.springjpabasics.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kurs.springjpabasics.command.CarCommand;
import pl.kurs.springjpabasics.command.UpdateCarCommand;
import pl.kurs.springjpabasics.dto.CarDTO;
import pl.kurs.springjpabasics.model.Car;
import pl.kurs.springjpabasics.repository.CarRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addCar(CarCommand command) {
        carRepository.save(modelMapper.map(command, Car.class));
    }

    @Override
    public void removeCar(int id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> modelMapper.map(car, CarDTO.class))
                .toList();
    }

    @Override
    public CarDTO updateCar(UpdateCarCommand updateCarCommand) {
        Car car = carRepository.findById(updateCarCommand.getId()).orElse(null);
        modelMapper.map(updateCarCommand, car);

        Car carToSave = carRepository.save(car);

        return modelMapper.map(carToSave, CarDTO.class);
    }
}
