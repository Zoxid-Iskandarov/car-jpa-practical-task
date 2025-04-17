package com.walking.carpractice.converter.car;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Car;
import com.walking.carpractice.model.car.request.UpdateCarRequest;

public class UpdateCarRequestConverter extends AbstractConverter<UpdateCarRequest, Car> {
    @Override
    public Car convert(UpdateCarRequest source) {
        Car car = new Car();

        car.setId(source.getId());
        car.setNumber(source.getNumber());
        car.setColor(source.getColor());
        car.setActualTechnicalInspection(car.getActualTechnicalInspection());

        return car;
    }
}
