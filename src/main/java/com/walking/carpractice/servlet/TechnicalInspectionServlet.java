package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.service.CarService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TechnicalInspectionServlet extends HttpServlet {
    private CarService carService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.carService = (CarService) servletContext.getAttribute(ContextAttributeNames.CAR_SERVICE);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        var year = Integer.parseInt(request.getParameter("year"));

        carService.resetTechnicalInspectionByYear(year);
    }
}
