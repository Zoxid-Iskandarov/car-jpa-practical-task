package com.walking.carpractice.converter.user;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.User;
import com.walking.carpractice.model.dto.user.request.UpdateUserRequest;

public class UpdateUserRequestConverter extends AbstractConverter<UpdateUserRequest, User> {
    @Override
    public User convert(UpdateUserRequest source) {
        User user = new User();

        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());

        return user;
    }
}
