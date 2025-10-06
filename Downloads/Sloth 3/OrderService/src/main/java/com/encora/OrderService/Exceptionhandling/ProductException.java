package com.encora.OrderService.Exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductException extends RuntimeException {
    public ProductException(String msg) {
        super(msg);
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
 class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
