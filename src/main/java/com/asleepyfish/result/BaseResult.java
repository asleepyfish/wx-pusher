package com.asleepyfish.result;

/**
 * 基本结果
 *
 * @Author: asleepyfish
 * @Date: 2022/9/6 14:36
 * @Description: 返回结果
 */
public class BaseResult<T> implements Result<T> {
    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 数据
     */
    private T result;

    private BaseResult() {
        this.errorCode = SUCCESS;
        this.errorMessage = "";
    }

    private BaseResult(T result) {
        this();
        this.setResult(result);
    }

    /**
     * 基本结果(错误信息)
     *
     * @param code    代码
     * @param message 消息
     */
    private BaseResult(String code, String message) {
        this.setErrorCode(code);
        this.setErrorMessage(message);
    }

    /**
     * 成功
     *
     * @return {@link BaseResult}<{@link T}>
     */
    public static <T> BaseResult<T> success() {
        return new BaseResult<>();
    }

    /**
     * 成功
     *
     * @param result 结果
     * @return {@link BaseResult}<{@link T}>
     */
    public static <T> BaseResult<T> success(T result) {
        return new BaseResult<>(result);
    }

    /**
     * 失败
     *
     * @param errorCode    错误代码
     * @param errorMessage 错误消息
     * @return {@link BaseResult}<{@link T}>
     */
    public static <T> BaseResult<T> fail(String errorCode, String errorMessage) {
        return new BaseResult<>(errorCode, errorMessage);
    }

    /**
     * 失败
     *
     * @param errorMessage 错误消息
     * @return {@link BaseResult}<{@link T}>
     */
    public static <T> BaseResult<T> fail(String errorMessage) {
        return fail(FAILURE, errorMessage);
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public T getResult() {
        return this.result;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void setResult(T result) {
        this.result = result;
    }
}
