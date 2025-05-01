package com.walking.carpractice.converter.brand;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Brand;
import com.walking.carpractice.model.dto.brand.request.CreateBrandRequest;

public class CreateBrandRequestConverter extends AbstractConverter<CreateBrandRequest, Brand> {
    @Override
    public Brand convert(CreateBrandRequest source) {
        Brand brand = new Brand();

        brand.setName(source.getName());

        return brand;
    }
}
