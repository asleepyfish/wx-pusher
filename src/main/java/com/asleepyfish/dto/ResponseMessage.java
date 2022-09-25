package com.asleepyfish.dto;

import lombok.Data;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/27 15:45
 * @Description: 返回信息
 */
@Data
public class ResponseMessage {
    /**
     * 开发者微信号
     */
    private String toUserName;
    /**
     * 发送方账号
     */
    private String fromUserName;
    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息文本
     */
    private String content;
}
