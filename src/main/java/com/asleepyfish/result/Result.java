package com.asleepyfish.result;

/**
 * @Author: asleepyfish
 * @Date: 2022/9/6 14:32
 * @Description: 通用返回接口
 */
public interface Result<T> {
    /**
     * 成功
     */
    String SUCCESS = "0";

    /**
     * 失败
     */
    String FAILURE = "-1";

    /**
     * 获取代码
     *
     * @return {@link String}
     */
    String getErrorCode();

    /**
     * 得到消息
     *
     * @return {@link String}
     */
    String getErrorMessage();

    /**
     * 获取数据
     *
     * @return {@link T}
     */
    T getResult();

    /**
     * 设置错误代码
     *
     * @param errorCode 错误代码
     */
    void setErrorCode(String errorCode);

    /**
     * 设置错误消息
     *
     * @param errorMessage 错误消息
     */
    void setErrorMessage(String errorMessage);

    /**
     * 集数据
     *
     * @param data 数据
     */
    void setResult(T data);
}
