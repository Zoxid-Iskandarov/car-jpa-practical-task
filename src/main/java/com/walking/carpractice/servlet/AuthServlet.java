package com.walking.carpractice.servlet;

import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.domain.User;
import com.walking.carpractice.exception.AuthException;
import com.walking.carpractice.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var servletContext = config.getServletContext();

        this.userService = (UserService) servletContext.getAttribute(ContextAttributeNames.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var username = request.getParameter("username");
        var password = request.getParameter("password");

        try {
            User user = userService.auth(username, password);

            var session = request.getSession(true);

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
        } catch (AuthException e) {
            handleError(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("./login");
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(401);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
