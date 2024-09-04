package pl.kurs.springjpabasics.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.kurs.springjpabasics.command.CarCommand;
import pl.kurs.springjpabasics.command.UpdateCarCommand;
import pl.kurs.springjpabasics.dto.CarDTO;
import pl.kurs.springjpabasics.model.Car;
import pl.kurs.springjpabasics.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void checkIfAllCarsAreReturned() {
        //given
        Car car = new Car();
        car.setId(1);
        car.setModel("S5");
        car.setProducer("Audi");

        CarDTO car2 = new CarDTO();
        car2.setId(1);
        car2.setModel("S5");
        car2.setProducer("Audi");

        doReturn(List.of(car)).when(carRepository).findAll();
        doReturn(car2).when(modelMapper).map(car, CarDTO.class);

        //when
        List<CarDTO> actualList = carService.getAllCars();

        //then
        assertAll(
                () -> assertEquals(actualList.get(0).getModel(), "S5"),
                () -> verifyNoMoreInteractions(carRepository),
                () -> assertEquals(actualList.size(), 1)
        );

    }

    @Test
    public void checkIfCarsAreRemoved() {
        //given
        int carId = 1;

        //when
        carService.removeCar(carId);

        //then
        verify(carRepository, times(1)).deleteById(carId);

    }

    @Test
    public void checkIfCarsAreAdded() {
        //given
        CarCommand carCommand = new CarCommand();
        carCommand.setModel("S5");
        carCommand.setProducer("Audi");

        Car car = new Car();
        car.setModel("S5");
        car.setProducer("Audi");

        doReturn(car).when(modelMapper).map(carCommand, Car.class);

        //when
        carService.addCar(carCommand);

        //then
        verify(modelMapper, times(1)).map(carCommand, Car.class);
        verify(carRepository, times(1)).save(car);


    }

    @Test
    public void checkIfCarsAreUpdated() {
        // Given
        UpdateCarCommand updateCarCommand = new UpdateCarCommand();
        updateCarCommand.setId(1);
        updateCarCommand.setProducer("Audi");
        updateCarCommand.setModel("A6");

        Car existingCar = new Car();
        existingCar.setId(1);
        existingCar.setProducer("Old Producer");
        existingCar.setModel("Old Model");

        Car updatedCar = new Car();
        updatedCar.setId(1);
        updatedCar.setProducer("Audi");
        updatedCar.setModel("A6");

        CarDTO carDTO = new CarDTO();
        carDTO.setId(1);
        carDTO.setProducer("Audi");
        carDTO.setModel("A6");

        when(carRepository.findById(updateCarCommand.getId())).thenReturn(Optional.of(existingCar));

        doNothing().when(modelMapper).map(updateCarCommand, existingCar);

        when(carRepository.save(existingCar)).thenReturn(updatedCar);
        when(modelMapper.map(updatedCar, CarDTO.class)).thenReturn(carDTO);

        // When
        CarDTO result = carService.updateCar(updateCarCommand);

        // Then
        assertAll(

                () -> assertEquals(1, result.getId()),
                () -> assertEquals("Audi", result.getProducer()),
                () -> assertEquals("A6", result.getModel()),
                () -> verify(carRepository, times(1)).findById(updateCarCommand.getId()),
                () -> verify(carRepository, times(1)).save(existingCar),
                () -> verify(modelMapper, times(1)).map(updateCarCommand, existingCar),
                () -> verify(modelMapper, times(1)).map(updatedCar, CarDTO.class)
        );

    }
}