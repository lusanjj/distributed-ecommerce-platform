package com.ecommerce.userservice.util;

import com.ecommerce.userservice.response.ResponseWrapper;

/**
 * ResponseUtil: 统一响应工具类。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 13:10
 * @Version 1.0
 */
public class ResponseUtil {

    public static <T> ResponseWrapper<T> success(String message, T data) {
        return new ResponseWrapper<>("success", message, data);
    }

    public static <T> ResponseWrapper<T> fail(String message, T data) {
        return new ResponseWrapper<>("fail", message, data);
    }
}
