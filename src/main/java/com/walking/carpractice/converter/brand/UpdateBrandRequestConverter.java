package com.walking.carpractice.converter.brand;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.domain.Brand;
import com.walking.carpractice.model.brand.request.UpdateBrandRequest;

public class UpdateBrandRequestConverter extends AbstractConverter<UpdateBrandRequest, Brand> {
    @Override
    public Brand convert(UpdateBrandRequest source) {
        Brand brand = new Brand();

        brand.setId(source.getId());
        brand.setName(source.getName());

        return brand;
    }
}
