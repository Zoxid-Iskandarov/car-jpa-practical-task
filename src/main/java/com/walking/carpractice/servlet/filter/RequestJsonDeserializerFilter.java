package com.walking.carpractice.servlet.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walking.carpractice.constant.ContextAttributeNames;
import com.walking.carpractice.model.brand.request.CreateBrandRequest;
import com.walking.carpractice.model.brand.request.UpdateBrandRequest;
import com.walking.carpractice.model.car.request.CreateCarRequest;
import com.walking.carpractice.model.car.request.UpdateCarRequest;
import com.walking.carpractice.model.model.request.CreateModelRequest;
import com.walking.carpractice.model.model.request.UpdateModelRequest;
import com.walking.carpractice.model.user.request.CreateUserRequest;
import com.walking.carpractice.model.user.request.UpdateUserRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestJsonDeserializerFilter extends HttpFilter {
    public static final String POJO_REQUEST_BODY = "pojoRequestBody";

    private Map<String, TypeReference<?>> targetTypes;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        this.objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextAttributeNames.OBJECT_MAPPER);

        initTargetTypes();
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!"application/json".equals(request.getContentType()) || request.getContentLength() == 0) {
            chain.doFilter(request, response);
            return;
        }

        byte[] jsonBody = request.getInputStream().readAllBytes();

        try {
            var targetType = getTargetType(request);
            Object pojoBody = objectMapper.readValue(jsonBody, targetType);

            request.setAttribute(POJO_REQUEST_BODY, pojoBody);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка десериализации запроса", e);
        }

        chain.doFilter(request, response);
    }

    private TypeReference<?> getTargetType(HttpServletRequest request) {
        var key = "%s&&%s".formatted(request.getServletPath(), request.getMethod());

        return targetTypes.getOrDefault(key, new TypeReference<>() {
        });
    }

    private void initTargetTypes() {
        targetTypes = new ConcurrentHashMap<>();

        targetTypes.put("/car&&POST", new TypeReference<CreateCarRequest>() {
        });
        targetTypes.put("/car&&PUT", new TypeReference<UpdateCarRequest>() {
        });
        targetTypes.put("/signUp&&POST", new TypeReference<CreateUserRequest>() {
        });
        targetTypes.put("/user&&PUT", new TypeReference<UpdateUserRequest>() {
        });
        targetTypes.put("/brand&&POST", new TypeReference<CreateBrandRequest>() {
        });
        targetTypes.put("/brand&&PUT", new TypeReference<UpdateBrandRequest>() {
        });
        targetTypes.put("/model&&POST", new TypeReference<CreateModelRequest>() {
        });
        targetTypes.put("/model&&PUT", new TypeReference<UpdateModelRequest>() {
        });
    }
}
