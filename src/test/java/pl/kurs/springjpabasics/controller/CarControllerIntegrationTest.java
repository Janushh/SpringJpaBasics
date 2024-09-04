package pl.kurs.springjpabasics.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.springjpabasics.model.Car;
import pl.kurs.springjpabasics.repository.CarRepository;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testApiResponse() throws Exception {
        //given
        Car car = new Car(1, "Audi", "S4");
        carRepository.save(car);


        //then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1," +
                        "\"producer\":\"Audi\"," +
                        "\"model\":\"S4\"}]"));

    }

    @Test
    public void testApiResponsePost() throws Exception {

        //given
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/cars")
                                .content("{\n" +
                                        "    \"producer\": \"BMW\",\n" +
                                        "    \"model\": \"3\"\n" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());


        //then

        Car car = carRepository.findById(2).get();

        assertAll(
                () -> assertEquals(car.getProducer(), "BMW"),
                () -> assertEquals(car.getModel(), "3")
        );
    }

    @Test
    public void testApiResponseGet() throws Exception {
        //given
        Car car = new Car(1, "Audi", "S4");
        carRepository.save(car);

        //then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"producer\":\"Audi\",\"model\":\"S4\"}]"));

    }

    @Test
    public void testApiResponsePut() throws Exception {
        //given
        Car car = new Car(1, "Audi", "S4");
        carRepository.save(car);

        String updatedCarJson = "{\"id\":1,\"producer\":\"Audi\",\"model\":\"A6\"}";

        //when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/cars")
                        .content(updatedCarJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        Car updatedCar = carRepository.findById(1).orElse(null);

        assertAll(
                () -> assertEquals("Audi", updatedCar.getProducer()),
                () -> assertEquals("A6", updatedCar.getModel())
        );
    }

    @Test
    public void testApiResponseDelete() throws Exception {
        //given
        Car car = new Car(1, "Audi", "S4");
        carRepository.save(car);

        // when
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/cars/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        boolean carExists = carRepository.existsById(1);

        assertFalse(carExists);
    }
}