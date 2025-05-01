package com.walking.carpractice.converter.brand;

import com.walking.carpractice.converter.AbstractConverter;
import com.walking.carpractice.model.dto.brand.BrandStatisticsDto;
import com.walking.carpractice.model.projection.BrandStatistics;

public class BrandStatisticsConverter extends AbstractConverter<BrandStatistics, BrandStatisticsDto> {
    @Override
    public BrandStatisticsDto convert(BrandStatistics source) {
        var target = new BrandStatisticsDto();

        target.setId(source.id());
        target.setModelCount(source.modelCount());

        return target;
    }
}
