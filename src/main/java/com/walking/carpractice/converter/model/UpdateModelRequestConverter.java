package com.walking.carpractice.converter.model;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Model;
import com.walking.carpractice.model.model.request.UpdateModelRequest;

public class UpdateModelRequestConverter extends AbstractConverter<UpdateModelRequest, Model> {
    @Override
    public Model convert(UpdateModelRequest source) {
        var model = new Model();

        model.setId(source.getId());
        model.setName(source.getName());

        return model;
    }
}
