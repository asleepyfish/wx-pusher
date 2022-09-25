package com.asleepyfish.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/24 19:27
 * @Description: 订阅相关服务接口
 */
public interface SubscribeService {
    void checkToken(HttpServletRequest request, HttpServletResponse response);
}
