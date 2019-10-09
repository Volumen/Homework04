package com.example.homework04;

import com.example.homework04.model.Car;
import com.example.homework04.model.Color;
import com.example.homework04.model.Mark;
import com.example.homework04.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataLoader implements CommandLineRunner {

    private CarRepository carRepository;
    public DataLoader(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        carRepository.getCars().add(new Car(1L, Mark.OPEL,"Astra", Color.GREEN));
        carRepository.getCars().add(new Car(2L,Mark.FIAT,"Punto",Color.BLACK));
        carRepository.getCars().add(new Car(3L,Mark.MERCEDES,"GLC",Color.RED));
    }
}
