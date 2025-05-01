package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.user.UpdateUserRequestConverter;
import com.walking.carpractice.converter.user.UserConverter;
import com.walking.carpractice.model.dto.user.request.UpdateUserRequest;
import com.walking.carpractice.service.UserService;
import com.walking.carpractice.servlet.filter.RequestJsonDeserializerFilter;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserServlet extends HttpServlet {
    private UserService userService;

    private UserConverter userConverter;
    private UpdateUserRequestConverter updateUserRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);

        this.userConverter = (UserConverter) servletContext.getAttribute(ContextAttributeNames.USER_CONVERTER);
        this.updateUserRequestConverter = (UpdateUserRequestConverter) servletContext.getAttribute
                (ContextAttributeNames.UPDATE_USER_REQUEST_CONVERTER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = Long.valueOf(request.getParameter("id"));

        var user = userService.getById(id);
        var userDto = userConverter.convert(user);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, userDto);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var userRequest = (UpdateUserRequest) request.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);
        var user = updateUserRequestConverter.convert(userRequest);

        var userId = (Long) request.getSession(false)
                .getAttribute("userId");
        user.setId(userId);

        var updatedUser = userService.update(user, userRequest.getCarIds());
        var userDto = userConverter.convert(updatedUser);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, userDto);
    }
}
