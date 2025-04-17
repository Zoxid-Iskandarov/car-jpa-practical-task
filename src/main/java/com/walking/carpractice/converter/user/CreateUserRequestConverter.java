package com.walking.carpractice.converter.user;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.User;
import com.walking.carpractice.model.user.request.CreateUserRequest;

public class CreateUserRequestConverter extends AbstractConverter<CreateUserRequest, User> {
    @Override
    public User convert(CreateUserRequest source) {
        User user = new User();

        user.setUsername(source.getUsername());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setPassword(source.getPassword());

        return user;
    }
}
