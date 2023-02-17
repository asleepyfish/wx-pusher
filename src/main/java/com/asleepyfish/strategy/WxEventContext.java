package com.asleepyfish.strategy;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/31 19:34
 * @Description: 容器类，用来选择使用哪种策略
 */
@Service
public class WxEventContext {
    @Resource
    private Map<String, WxEventStrategy> strategyMap;

    public void execute(String strategy, Map<String, String> requestMap, HttpServletResponse response) throws Exception {
        strategyMap.get(strategy).execute(requestMap, response);
    }
}
