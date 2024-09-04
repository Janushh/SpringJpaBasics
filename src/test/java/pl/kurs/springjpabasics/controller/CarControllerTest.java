package pl.kurs.springjpabasics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kurs.springjpabasics.command.UpdateCarCommand;
import pl.kurs.springjpabasics.dto.CarDTO;
import pl.kurs.springjpabasics.service.CarService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CarDTO carDTO;

    @MockBean
    private CarService carService;

    @Test
    public void testApiResponse() throws Exception {
        //given
        doReturn(1).when(carDTO).getId();
        doReturn("Audi").when(carDTO).getProducer();
        doReturn("S4").when(carDTO).getModel();

        doReturn(List.of(carDTO)).when(carService).getAllCars();

        //when/then
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
    public void testDeleteCar() throws Exception {
        //given
        int carId = 1;
        doNothing().when(carService).removeCar(carId);

        doReturn(List.of(carDTO)).when(carService).getAllCars();

        //when/then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/cars/{id}", carId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCars() throws Exception {
        //given
        doReturn(List.of(carDTO)).when(carService).getAllCars();

        //when/then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateCar() throws Exception {
        // given
        UpdateCarCommand command = new UpdateCarCommand();
        command.setId(1);
        command.setProducer("Audi");
        command.setModel("Rs3");

        CarDTO updatedCar = new CarDTO();
        updatedCar.setId(1);
        updatedCar.setProducer("Audi");
        updatedCar.setModel("Rs3");

        doReturn(updatedCar).when(carService).updateCar(any(UpdateCarCommand.class));

        // when/then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1," +
                        "\"producer\":\"Audi\"," +
                        "\"model\":\"Rs3\"}"));
    }
}