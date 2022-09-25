package com.asleepyfish.enums;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-22 23:19
 * @Description: 模板的通知类型（给那些人发送模板）
 */
public enum WxInformType {
    /**
     * all
     */
    ALL("all", "给所有订阅者发送"),

    /**
     * special
     */
    SPECIAL("special", "只给SpecialOpenId发送"),

    /**
     * other
     */
    OTHER("other", "给SpecialOpenId以外的人发送");
    private final String type;

    private final String description;

    WxInformType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
