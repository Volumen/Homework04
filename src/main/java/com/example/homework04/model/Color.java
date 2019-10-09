package com.example.homework04.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
    GREEN("green"),
    BLACK("black"),
    RED("red"),
    BLUE("blue"),
    WHITE("white"),
    YELLOW("yellow");

    private String color;


    Color(String color)
    {
        this.color = color;
    }
    @JsonValue
    public String getColor()
    {
        return color;
    }

}
