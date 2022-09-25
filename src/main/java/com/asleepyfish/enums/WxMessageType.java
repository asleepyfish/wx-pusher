package com.asleepyfish.enums;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/25 19:38
 * @Description: 微信消息类型
 */
public enum WxMessageType {
    /**
     * 发送消息
     */
    TEXT("text"),

    /**
     * 事件类型
     */
    EVENT("event");
    private final String type;

    WxMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
