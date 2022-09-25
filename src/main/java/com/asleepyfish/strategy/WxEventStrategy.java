package com.asleepyfish.strategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/31 19:15
 * @Description: 策略接口
 */
public interface WxEventStrategy {
    /**
     * 执行
     *
     * @param requestMap 请求映射
     * @param response   响应
     * @throws IOException exception
     */
    void execute(Map<String, String> requestMap, HttpServletResponse response) throws Exception;
}
