package com.walking.carpractice.model.car.request;

public class UpdateCarRequest {
    private Long id;

    private String number;

    private String color;

    private boolean actualTechnicalInspection;


    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }

    public boolean isActualTechnicalInspection() {
        return actualTechnicalInspection;
    }
}
