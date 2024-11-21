package com.pos.point_of_sale.common.ResponseBodyAdvices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.pos.point_of_sale.common.interceptors.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final HttpServletResponse httpServletResponse;

    public ApiResponseBodyAdvice(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @SuppressWarnings({ "null", "rawtypes" })
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        int statusCode = httpServletResponse.getStatus();

        if (body instanceof ApiResponse) {
            // If the response is already an ApiResponse, update its status code
            ApiResponse<?> apiResponse = (ApiResponse<?>) body;
            apiResponse.setCode(statusCode);
            return apiResponse;
        }

        // Wrap the response body in an ApiResponse with the status code
        return new ApiResponse<>(statusCode, "success", "Request successful", body);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
