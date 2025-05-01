package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.model.ModelConverter;
import com.walking.carpractice.service.ModelService;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ModelSearchServlet extends HttpServlet {
    private ModelService modelService;
    private ModelConverter modelConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.modelService = (ModelService) servletContext.getAttribute(ContextAttributeNames.MODEL_SERVICE);
        this.modelConverter = (ModelConverter) servletContext.getAttribute(ContextAttributeNames.MODEL_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var brandId = Long.valueOf(request.getParameter("brandId"));

        var models = modelService.getAllByBrandId(brandId);
        var modelDtos = modelConverter.convert(models);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, modelDtos);
    }
}
