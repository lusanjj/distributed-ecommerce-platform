package com.ecommerce.userservice.exception;

import com.ecommerce.userservice.response.ResponseWrapper;
import com.ecommerce.userservice.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler: 统一异常处理类。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 15:20
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 JWT 相关异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseUtil.fail(ex.getMessage(), null));
    }

    /**
     * 处理其他未捕获的异常
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseUtil.fail("Internal Server Error: " + ex.getMessage(), null));
    }
}
