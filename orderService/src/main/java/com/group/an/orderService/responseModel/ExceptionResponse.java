package com.group.an.orderService.responseModel;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExceptionResponse {
    int status;
    String message;
}
