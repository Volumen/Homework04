package com.example.homework04.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Mark {
    OPEL("Opel"),
    MERCEDES("Mercedes"),
    FIAT("Fiat"),
    SKODA("Skoda");

    private String mark;
    Mark(String mark) {
        this.mark = mark;
    }
    @JsonValue
    public String getMark()
    {
        return mark;
    }


}
