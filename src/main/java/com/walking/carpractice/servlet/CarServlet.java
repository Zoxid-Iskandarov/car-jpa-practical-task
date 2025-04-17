package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.car.CarConverter;
import com.walking.carpractice.converter.car.CreateCarRequestConverter;
import com.walking.carpractice.converter.car.UpdateCarRequestConverter;
import com.walking.carpractice.model.car.request.CreateCarRequest;
import com.walking.carpractice.model.car.request.UpdateCarRequest;
import com.walking.carpractice.service.CarService;
import com.walking.carpractice.servlet.filter.RequestJsonDeserializerFilter;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CarServlet extends HttpServlet {
    private CarService carService;

    private CarConverter carConverter;
    private CreateCarRequestConverter createCarRequestConverter;
    private UpdateCarRequestConverter updateCarRequestConverter;

    @Override
    public void init(ServletConfig event) throws ServletException {
        var servletContext = event.getServletContext();

        this.carService = (CarService) servletContext.getAttribute(ContextAttributeNames.CAR_SERVICE);
        this.carConverter = (CarConverter) servletContext.getAttribute(ContextAttributeNames.CAR_CONVERTER);

        this.createCarRequestConverter = (CreateCarRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_CAR_REQUEST_CONVERTER);

        this.updateCarRequestConverter = (UpdateCarRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.UPDATE_CAR_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = Long.parseLong(request.getParameter("id"));

        var car = carService.getById(id);
        var carDto = carConverter.convert(car);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, carDto);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var createCarRequest = (CreateCarRequest) request.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var car = createCarRequestConverter.convert(createCarRequest);

        var createdCar = carService.create(car);
        var carDto = carConverter.convert(createdCar);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, carDto);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var updatedCarRequest = (UpdateCarRequest) request.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var car = updateCarRequestConverter.convert(updatedCarRequest);

        var updatedCar = carService.update(car);
        var carDto = carConverter.convert(updatedCar);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, carDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = Long.parseLong(request.getParameter("id"));

        carService.delete(id);
    }
}
