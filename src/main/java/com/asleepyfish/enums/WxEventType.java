package com.asleepyfish.enums;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/25 19:33
 * @Description: 记录Event类型
 */
public enum WxEventType {
    /**
     * 订阅
     */
    SUBSCRIBE("subscribe"),

    /**
     * 取消订阅
     */
    UNSUBSCRIBE("unsubscribe"),

    /**
     * 模板信息发送完成回调信息
     */
    TEMPLATE_SEND_JOB_FINISH("TEMPLATESENDJOBFINISH"),

    /**
     * 用户上报位置信息，用于天气api的使用
     */
    LOCATION("LOCATION");

    private final String type;

    WxEventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
