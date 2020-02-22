package com.example.homework04;

import com.example.homework04.controller.CarController;
import com.example.homework04.model.Car;
import com.example.homework04.model.Color;
import com.example.homework04.model.Mark;
import com.example.homework04.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {

    MockMvc mockMvc;

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Before
    public void setup() {
        //MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void getAllCar() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(1L, Mark.FIAT,"punto", Color.GREEN));
        cars.add(new Car(2L, Mark.FIAT,"puntoo", Color.RED));

        when(carService.getCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));

        verify(carService, times(1)).getCars();
    }

    @Test
    public void addCar() throws Exception {
        Car car = new Car(4L,Mark.OPEL,"Astra",Color.BLACK);

        when(carService.addCar(ArgumentMatchers.any(Car.class))).thenReturn(true);

        mockMvc.perform(post("/cars")
                .content(asJsonString(car))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getCarById() throws Exception {
        Car car = new Car(4L,Mark.OPEL,"Astra",Color.BLACK);

        when(carService.getCarById(anyLong())).thenReturn(car);

        mockMvc.perform(get("/cars/{id}",4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark").value("Opel"))
                .andExpect(jsonPath("$.model").value("Astra"))
                .andExpect(jsonPath("$.color").value("black"));

        verify(carService,times(1)).getCarById(anyLong());
    }
    @Test
    public void deleteCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/{id}",2L))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteCar(anyLong());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
