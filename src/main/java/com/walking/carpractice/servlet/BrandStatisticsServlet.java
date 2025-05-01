package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.brand.BrandStatisticsConverter;
import com.walking.carpractice.service.BrandService;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BrandStatisticsServlet extends HttpServlet {
    private BrandService brandService;
    private BrandStatisticsConverter brandStatisticsConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.brandService = (BrandService) servletContext.getAttribute(ContextAttributeNames.BRAND_SERVICE);

        this.brandStatisticsConverter = (BrandStatisticsConverter) servletContext.getAttribute
                (ContextAttributeNames.BRAND_STATISTICS_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var statistics = brandService.getStatistics();
        var statisticsDtos = brandStatisticsConverter.convert(statistics);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, statisticsDtos);
    }
}
