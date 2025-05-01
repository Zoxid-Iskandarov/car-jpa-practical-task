package com.walking.carpractice.converter.brand;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Brand;
import com.walking.carpractice.model.dto.brand.BrandDto;

import java.time.ZoneOffset;

public class BrandConverter extends AbstractConverter<Brand, BrandDto> {
    @Override
    public BrandDto convert(Brand source) {
        var brandDto = new BrandDto();

        brandDto.setId(source.getId());
        brandDto.setName(source.getName());
        brandDto.setCreated(source.getCreated().atZone(ZoneOffset.UTC));
        brandDto.setUpdated(source.getUpdated().atZone(ZoneOffset.UTC));

        return brandDto;
    }
}
