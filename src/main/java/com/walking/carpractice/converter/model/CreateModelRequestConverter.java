package com.walking.carpractice.converter.model;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Model;
import com.walking.carpractice.model.model.request.CreateModelRequest;

public class CreateModelRequestConverter extends AbstractConverter<CreateModelRequest, Model> {
    @Override
    public Model convert(CreateModelRequest source) {
        Model model = new Model();

        model.setName(source.getName());
        model.setBrandId(source.getBrandId());

        return model;
    }
}
