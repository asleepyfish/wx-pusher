package com.asleepyfish.exception;

/**
 * @Author: asleepyfish
 * @Date: 2022/9/6 14:47
 * @Description: 基本异常类
 */
public class BaseException extends RuntimeException {
    private String errorCode = "-1";

    private String errorMessage = "";

    public BaseException() {
        super();
    }

    public BaseException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
