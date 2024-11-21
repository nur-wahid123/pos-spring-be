package com.pos.point_of_sale.common.interceptors;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResponseInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public ResponseInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Nothing needs to be done before handling the request
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        // The response is being processed, we can intercept here to modify it.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        // Check if the response status code indicates success
        int status = response.getStatus();
        ApiResponse<Object> apiResponse;

        if (status >= 200 && status < 300) {
            // Success Response
            apiResponse = new ApiResponse<>(status,"success", "Request successful", null); // Customize data as needed
        } else {
            // Error Response
            apiResponse = new ApiResponse<>(status,"error", "Something went wrong", null); // You can customize the error message
        }

        // Set the response type as JSON and write the ApiResponse to the response body
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        objectMapper.writeValue(writer, apiResponse);
        writer.flush();
    }

}
