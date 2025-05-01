package com.walking.carpractice.converter.user;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Car;
import com.walking.carpractice.domain.User;
import com.walking.carpractice.model.dto.user.UserDto;

import java.time.ZoneOffset;

public class UserConverter extends AbstractConverter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();

        userDto.setId(source.getId());
        userDto.setUsername(source.getUsername());
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());

        var carIds = source.getCars()
                .stream()
                .map(Car::getId)
                .toList();
        userDto.setCarIds(carIds);

        userDto.setCreated(source.getCreated().atZone(ZoneOffset.UTC));
        userDto.setUpdated(source.getUpdated().atZone(ZoneOffset.UTC));

        return userDto;
    }
}
