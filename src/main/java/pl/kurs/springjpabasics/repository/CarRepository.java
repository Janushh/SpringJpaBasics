package pl.kurs.springjpabasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.springjpabasics.model.Car;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Car> findByModel(String model);

//    @Query("select c from car c where brand == :brand ")
//    Optional<Car> findByBrand(String brand);
}
