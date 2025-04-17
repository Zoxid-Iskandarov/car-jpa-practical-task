package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.car.CarConverter;
import com.walking.carpractice.service.CarService;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CarSearchServlet extends HttpServlet {
    private CarService carService;
    private CarConverter carConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.carService = (CarService) servletContext.getAttribute(ContextAttributeNames.CAR_SERVICE);
        this.carConverter = (CarConverter) servletContext.getAttribute(ContextAttributeNames.CAR_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var userId = (Long) request.getSession(false)
                .getAttribute("userId");

        var cars = carService.getAllByUser(userId);
        var carDtos = carConverter.convert(cars);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, carDtos);
    }
}
