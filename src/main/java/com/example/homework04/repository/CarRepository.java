package com.example.homework04.repository;

import com.example.homework04.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {
    private List<Car> listOfCars;

    public CarRepository() {
        this.listOfCars = new ArrayList<>();
    }
    public List<Car> getCars()
    {
        return listOfCars;
    }
}
