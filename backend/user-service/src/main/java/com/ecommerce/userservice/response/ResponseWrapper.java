package com.ecommerce.userservice.response;

import java.time.LocalDateTime;

/**
 * ResponseWrapper: 统一响应封装类。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 14:10
 * @Version 1.0
 */
public class ResponseWrapper<T> {
    private String status; // 请求状态：success 或 fail
    private String message; // 消息说明
    private T data; // 实际返回的数据
    private LocalDateTime timestamp; // 时间戳

    public ResponseWrapper(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
