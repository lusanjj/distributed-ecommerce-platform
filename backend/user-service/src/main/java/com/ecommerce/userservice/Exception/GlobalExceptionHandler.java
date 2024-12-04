package com.ecommerce.userservice.util;

import com.ecommerce.userservice.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ExceptionHandler: 全局异常处理类。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 15:15
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>("fail", ex.getMessage(), null));
    }

    // 其他异常处理器可以在此添加
}
