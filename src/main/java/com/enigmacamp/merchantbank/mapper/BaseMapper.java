package com.enigmacamp.merchantbank.mapper;

import com.enigmacamp.merchantbank.base.BaseResponse;

public class BaseMapper {
    public static BaseResponse<?> mapToBaseResponse(String message, Integer statusCode, Object data) {
        return BaseResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .data(data)
                .build();
    }
}
