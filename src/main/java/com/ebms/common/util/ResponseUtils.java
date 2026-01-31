package com.ebms.common.util;

import com.ebms.common.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtils {

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> ApiResponse<T> failure(String message, HttpStatus status) {
        return new ApiResponse<>(status.value(), message, null);
    }
}
