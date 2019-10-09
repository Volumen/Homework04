package com.example.homework04.gui;

import com.example.homework04.model.Car;
import com.example.homework04.model.Color;
import com.example.homework04.model.Mark;
import com.example.homework04.service.CarService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;

@Route("cars-menu")
public class CarsGui extends VerticalLayout {

    private CarService carService;
    private Car tempCar;

    private TextField textFieldModel;
    private TextField textFieldId;

    private Button buttonAdd;
    private Button buttonDelete;
    private Button buttonModifyCar;
    private Button buttonModifyOneField;
    private Button buttonShowCarById;
    private Button buttonShowAllCars;

    private ComboBox<Mark> markComboBox;
    private ComboBox<Color> colorComboBox;

    private Notification notification;

    @Autowired
    public CarsGui(CarService carService) {
        this.carService = carService;

        notification = new Notification();
        notification.setDuration(2000);

        textFieldModel = new TextField("Model");
        textFieldId = new TextField("Id");

        markComboBox = new ComboBox<>("Mark");
        colorComboBox = new ComboBox<>("Color");

        buttonAdd = new Button("Add Car");
        buttonDelete = new Button("Delete Car");
        buttonModifyCar = new Button("ModifyCar");
        buttonModifyOneField = new Button("ModifyPosition");
        buttonShowCarById = new Button("ShowCarById");
        buttonShowAllCars = new Button("ShowAllCars");

        markComboBox.setItems(EnumSet.allOf(Mark.class));
        colorComboBox.setItems(EnumSet.allOf(Color.class));

        Details infoAboutApi = new Details("Info",
                new Text("If you want to add a car, you must complete all fields. " +
                        "If you want to remove the car, enter only the car id. " +
                        "If you want to modify an existing car, you must enter the car id and fill in the rest of the fields. " +
                        "If you want to modify a field, others may be empty. "));

        Grid<Car> grid = new Grid<>(Car.class);
        grid.setHeightByRows(true);
        grid.setItems(carService.getCars());

        //ClickListeners
        buttonAdd.addClickListener(clickEvent -> {
            tempCar = new Car();
            addCar(tempCar);
            grid.getDataProvider().refreshAll();
        });

        buttonDelete.addClickListener(clickEvent -> {
            deleteCar(Long.valueOf(textFieldId.getValue()));
            grid.getDataProvider().refreshAll();
        });

        buttonModifyCar.addClickListener(clickEvent -> {
            tempCar = new Car();
            modifyCar(tempCar);
            grid.getDataProvider().refreshAll();
        });

        buttonModifyOneField.addClickListener(clickEvent -> {
            tempCar = new Car();
            modifyCarField(tempCar);
            grid.getDataProvider().refreshAll();
        });

        buttonShowCarById.addClickListener(clickEvent -> {
            grid.setItems(carService.getCarById(Long.parseLong(textFieldId.getValue())));
        });
        buttonShowAllCars.addClickListener(clickEvent -> {
            grid.setItems(carService.getCars());
        });

        HorizontalLayout horizontalLayoutOne = new HorizontalLayout();
        horizontalLayoutOne.add(textFieldId,markComboBox,textFieldModel,colorComboBox);
        HorizontalLayout horizontalLayoutTwo = new HorizontalLayout();
        horizontalLayoutTwo.add(buttonAdd,buttonDelete,buttonModifyCar,buttonModifyOneField,buttonShowCarById,buttonShowAllCars, notification);

        add(horizontalLayoutOne,horizontalLayoutTwo,grid,infoAboutApi);
    }

    public void addCar(Car car)
    {
        saveCar(car);
        if(!colorComboBox.isEmpty() && !markComboBox.isEmpty() && !textFieldModel.isEmpty() && !textFieldId.isEmpty()) {
            carService.addCar(car);
            if (carService.getCars().contains(car)) {
                addNotification("New car has been added!");
            } else {
                addNotification("Something goes wrong with adding car!");
            }
        }
        else {addNotification("You must complete all fields to add a car");}
    }

    public void deleteCar(Long id)
    {
        carService.deleteCar(id);
        if(carService.getCarById(id)==null)
        {
            addNotification("Car has been removed!");
        }
        else
        {
            addNotification("Something goes wrong with deleting car!");
        }
    }
    public void modifyCar(Car car)
    {
        saveCar(car);
        if(!colorComboBox.isEmpty() && !markComboBox.isEmpty() && !textFieldModel.isEmpty() && !textFieldId.isEmpty()) {
            Boolean result = carService.modifyCarField(car);
            if (result) {
                addNotification("The car modification was successful!");
            } else {
                addNotification("Something goes wrong with car modification!");
            }
        }else {addNotification("You must complete all fields to modify a car");}
    }
    public void modifyCarField(Car car)
    {
        saveCar(car);
        Boolean result = carService.modifyCarField(car);
        if(result)
        {
            addNotification("The car field modification was successful!");
        }
        else {
            addNotification("Something goes wrong with car field modification!");
        }
    }
    public void saveCar(Car car)
    {
        if(!colorComboBox.isEmpty()) {car.setColor(colorComboBox.getValue());}
        if(!markComboBox.isEmpty()) {car.setMark(markComboBox.getValue());}
        if(!textFieldModel.isEmpty()) {car.setModel(textFieldModel.getValue());}
        if(!textFieldId.isEmpty()) {car.setIdOfCar(Long.parseLong(textFieldId.getValue()));}
    }
    public void addNotification(String words)
    {
        notification.show(words);
        notification.open();
    }

}
