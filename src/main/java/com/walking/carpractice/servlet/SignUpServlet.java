package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.converter.user.CreateUserRequestConverter;
import com.walking.carpractice.converter.user.UserConverter;
import com.walking.carpractice.model.user.request.CreateUserRequest;
import com.walking.carpractice.service.UserService;
import com.walking.carpractice.servlet.filter.RequestJsonDeserializerFilter;
import com.walking.carpractice.servlet.filter.ResponseJsonSerializerFilter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private UserService userService;

    private UserConverter userConverter;
    private CreateUserRequestConverter createUserRequestConverter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);
        this.userConverter = (UserConverter) servletContext.getAttribute(ContextAttributeNames.USER_CONVERTER);

        this.createUserRequestConverter = (CreateUserRequestConverter) servletContext.getAttribute(
                ContextAttributeNames.CREATE_USER_REQUEST_CONVERTER);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var userRequest = (CreateUserRequest) request.getAttribute(RequestJsonDeserializerFilter.POJO_REQUEST_BODY);

        var user = createUserRequestConverter.convert(userRequest);

        var createdUser = userService.create(user);
        var userDto = userConverter.convert(createdUser);

        request.setAttribute(ResponseJsonSerializerFilter.POJO_RESPONSE_BODY, userDto);
    }
}
