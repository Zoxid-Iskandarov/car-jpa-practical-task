package com.walking.carpractice.model.car.request;

public class CreateCarRequest {
    private String number;

    private int year;

    private String color;

    private boolean actualTechnicalInspection;

    private Long modelId;

    public String getNumber() {
        return number;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public boolean isActualTechnicalInspection() {
        return actualTechnicalInspection;
    }

    public Long getModelId() {
        return modelId;
    }
}
