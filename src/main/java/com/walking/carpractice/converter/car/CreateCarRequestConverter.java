package com.walking.carpractice.converter.car;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Car;
import com.walking.carpractice.model.dto.car.request.CreateCarRequest;

public class CreateCarRequestConverter extends AbstractConverter<CreateCarRequest, Car> {
    @Override
    public Car convert(CreateCarRequest source) {
        Car car = new Car();

        car.setNumber(source.getNumber());
        car.setYear(source.getYear());
        car.setColor(source.getColor());
        car.setActualTechnicalInspection(source.isActualTechnicalInspection());
        car.setModelId(source.getModelId());

        return car;
    }
}
