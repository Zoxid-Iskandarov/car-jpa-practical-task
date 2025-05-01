package com.walking.carpractice.converter.car;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Car;
import com.walking.carpractice.domain.User;
import com.walking.carpractice.model.dto.car.CarDto;

import java.time.ZoneOffset;

public class CarConverter extends AbstractConverter<Car, CarDto> {
    @Override
    public CarDto convert(Car source) {
        CarDto carDto = new CarDto();

        carDto.setId(source.getId());
        carDto.setNumber(source.getNumber());
        carDto.setYear(source.getYear());
        carDto.setColor(source.getColor());
        carDto.setActualTechnicalInspection(source.getActualTechnicalInspection());

        var ownerIds = source.getOwners()
                .stream()
                .map(User::getId)
                .toList();
        carDto.setOwnerIds(ownerIds);

        carDto.setCreated(source.getCreated().atZone(ZoneOffset.UTC));
        carDto.setUpdated(source.getUpdated().atZone(ZoneOffset.UTC));

        return carDto;
    }
}
