package com.example.homework04.repository;

import com.example.homework04.model.Car;
import com.example.homework04.model.Color;
import com.example.homework04.model.Mark;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {
    private List<Car> listOfCars;

    public CarRepository() {
        this.listOfCars = new ArrayList<>();
        listOfCars.add(new Car(1L, Mark.OPEL,"Astra", Color.GREEN));
        listOfCars.add(new Car(2L,Mark.FIAT,"Punto",Color.BLACK));
        listOfCars.add(new Car(3L,Mark.MERCEDES,"GLC",Color.RED));
    }
    public List<Car> getCars()
    {
        return listOfCars;
    }
}
