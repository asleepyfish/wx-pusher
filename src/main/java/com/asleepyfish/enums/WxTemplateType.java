package com.asleepyfish.enums;

import com.asleepyfish.common.WxTemplateConstants;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-06 21:25
 * @Description: 微信模板类型
 */
public enum WxTemplateType {
    /**
     * 特殊早安模板，给specialId发送
     */
    SPECIAL_MORNING(WxTemplateConstants.SPECIAL_MORNING, "特殊早安模板", WxInformType.SPECIAL, "只给SpecialOpenId发送"),

    /**
     * 普通早安模板，给除了SpecialId之外的其他订阅者发送
     */
    COMMON_MORNING(WxTemplateConstants.COMMON_MORNING, "普通早安模板", WxInformType.OTHER, "给SpecialOpenId以外的人发送");

    private final String templateId;

    private final String templateDescription;

    private final WxInformType informType;

    private final String informTypeDescription;

    WxTemplateType(String templateId, String templateDescription, WxInformType informType, String informTypeDescription) {
        this.templateId = templateId;
        this.templateDescription = templateDescription;
        this.informType = informType;
        this.informTypeDescription = informTypeDescription;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public WxInformType getInformType() {
        return informType;
    }

    public String getInformTypeDescription() {
        return informTypeDescription;
    }
}
