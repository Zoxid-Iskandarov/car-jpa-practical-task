package com.walking.carpractice.servlet.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.exception.CommonAppException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseJsonSerializerFilter extends HttpFilter {
    public static final String POJO_RESPONSE_BODY = "pojoResponseBody";

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        this.objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextAttributeNames.OBJECT_MAPPER);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);

        var pojoBody = request.getAttribute(POJO_RESPONSE_BODY);

        if (pojoBody == null) {
            return;
        }

        try {
            response.getOutputStream()
                    .write(objectMapper.writeValueAsBytes(pojoBody));

            response.setContentType("application/json");
        } catch (IOException e) {
            throw new CommonAppException("Ошибка формирования тела ответа", e);
        }
    }
}
