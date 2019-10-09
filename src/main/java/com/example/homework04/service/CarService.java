package com.example.homework04.service;

import com.example.homework04.controller.CarController;
import com.example.homework04.model.Car;
import com.example.homework04.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
    this.carRepository=carRepository;
    }

    public List<Car> getCars()
    {
        return carRepository.getCars();
    }

    public Car getCarById(long id)
    {
        Optional<Car> firstCar = carRepository
                .getCars()
                .stream()
                .filter(car -> car.getIdOfCar()==id)
                .findFirst();
        if(firstCar.isPresent())
        {
            return firstCar.get();
        }
        return null;
    }
    public List<Car> getCarsByColor(String color)
    {
        List<Car> listOfCarsByColor = carRepository
                .getCars()
                .stream()
                .filter(car -> car
                        .getColor().toString().equalsIgnoreCase(color))
                .collect(Collectors.toList());
        if(!listOfCarsByColor.isEmpty())
        {
            System.out.println("nie jest pusty");
            return listOfCarsByColor;
        }
        System.out.println("jest pusty");
        return null;
    }

    public Boolean addCar(Car car)
    {
        return carRepository.getCars().add(car);
    }

    public Boolean deleteCar(long id)
    {
        Optional<Car> optionalCar = carRepository.getCars()
                .stream()
                .filter(car -> car.getIdOfCar()==id)
                .findFirst();
        if(optionalCar.isPresent())
        {

            carRepository.getCars().remove(optionalCar.get());
            System.out.println(optionalCar);
            return true;
        }
        return false;
    }

    public Boolean modifyCarField(Car carToModify)
    {
        Optional<Car> optionalCar = carRepository.getCars()
                .stream()
                .filter(car -> car.getIdOfCar() == carToModify.getIdOfCar())
                .findFirst();
        if(optionalCar.isPresent())
        {

            if(carToModify.getMark() != null){optionalCar.get().setMark(carToModify.getMark());}
            if(carToModify.getModel() != null){optionalCar.get().setModel(carToModify.getModel());}
            if(carToModify.getColor() != null){optionalCar.get().setColor(carToModify.getColor());}
            return true;
        }
        return false;
    }
    public Boolean modifyCar(Car carToModify)
    {
        Optional<Car> optionalCar = carRepository.getCars()
                .stream()
                .filter(car -> car.getIdOfCar() == carToModify.getIdOfCar())
                .findFirst();
        if(optionalCar.isPresent())
        {
            System.out.println("Jest obecny");
            if(carToModify.getMark() != null && carToModify.getModel() != null && carToModify.getColor() != null)
            {
            optionalCar.get().setMark(carToModify.getMark());
            optionalCar.get().setModel(carToModify.getModel());
            optionalCar.get().setColor(carToModify.getColor());
                return true;
            }
            return false;
        }
        System.out.println("nie Jest obecny");
        return false;
    }
}
