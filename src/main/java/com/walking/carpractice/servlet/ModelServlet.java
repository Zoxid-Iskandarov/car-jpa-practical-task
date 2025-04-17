package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.model.CreateModelRequestConverter;
import com.walking.carpractice.converter.model.ModelConverter;
import com.walking.carpractice.converter.model.UpdateModelRequestConverter;
import com.walking.carpractice.model.model.request.CreateModelRequest;
import com.walking.carpractice.model.model.request.UpdateModelRequest;
import com.walking.carpractice.service.ModelService;
import com.walking.carpractice.servlet.filter.RequestJsonDeserializerFilter;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ModelServlet extends HttpServlet {
    private ModelService modelService;

    private ModelConverter modelConverter;
    private CreateModelRequestConverter createModelRequestConverter;
    private UpdateModelRequestConverter updateModelRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.modelService = (ModelService) servletContext.getAttribute(ContextAttributeNames.MODEL_SERVICE);

        this.modelConverter = (ModelConverter) servletContext.getAttribute(ContextAttributeNames.MODEL_CONVERTER);

        this.createModelRequestConverter = (CreateModelRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_MODEL_REQUEST_CONVERTER);
        this.updateModelRequestConverter = (UpdateModelRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.UPDATE_MODEL_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = Long.parseLong(request.getParameter("id"));

        var model = modelService.getById(id);
        var modelDto = modelConverter.convert(model);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, modelDto);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var modelRequest = (CreateModelRequest) request.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var model = createModelRequestConverter.convert(modelRequest);

        var createdModel = modelService.create(model);
        var modelDto = modelConverter.convert(createdModel);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, modelDto);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var modelRequest = (UpdateModelRequest) request.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var model = updateModelRequestConverter.convert(modelRequest);

        var updatedModel = modelService.update(model);
        var modelDto = modelConverter.convert(updatedModel);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, modelDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = Long.parseLong(request.getParameter("id"));
        modelService.delete(id);
    }
}
