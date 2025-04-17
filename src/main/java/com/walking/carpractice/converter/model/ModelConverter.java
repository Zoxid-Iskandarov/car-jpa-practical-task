package com.walking.carpractice.converter.model;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Model;
import com.walking.carpractice.model.model.ModelDto;

import java.time.ZoneOffset;

public class ModelConverter extends AbstractConverter<Model, ModelDto> {
    @Override
    public ModelDto convert(Model source) {
        var modelDto = new ModelDto();

        modelDto.setId(source.getId());
        modelDto.setName(source.getName());
        modelDto.setBrandId(source.getBrandId());
        modelDto.setCreated(source.getCreated().atZone(ZoneOffset.UTC));
        modelDto.setUpdated(source.getUpdated().atZone(ZoneOffset.UTC));

        return modelDto;
    }
}
