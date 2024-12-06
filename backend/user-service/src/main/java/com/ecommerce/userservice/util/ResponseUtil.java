package com.ecommerce.userservice.util;
import com.ecommerce.userservice.response.ResponseWrapper;

/**
* ClassName: ResponseUtil
* Package:com.ecommerce.userservice.util
* Description:统一响应工具类。
* @Author Shane Liu
* @Create 2024/12/4 12:19
* @Version 1.0
*/


public class ResponseUtil {

    /**
     * 成功响应
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 格式化的成功响应
     */
    public static <T> ResponseWrapper<T> success(String message, T data) {
        return new ResponseWrapper<>("success", message, data);
    }

    /**
     * 失败响应
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 格式化的失败响应
     */
    public static <T> ResponseWrapper<T> fail(String message, T data) {
        return new ResponseWrapper<>("fail", message, data);
    }

    /**
     * 错误响应（不含数据）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 格式化的错误响应
     */
    public static <T> ResponseWrapper<T> error(String message) {
        return new ResponseWrapper<>("error", message, null);
    }
}
