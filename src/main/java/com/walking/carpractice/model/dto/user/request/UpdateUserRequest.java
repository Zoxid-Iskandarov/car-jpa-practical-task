package com.walking.carpractice.model.dto.user.request;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserRequest {
    private String firstName;

    private String lastName;

    private List<Long> carIds = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Long> getCarIds() {
        return carIds;
    }
}
