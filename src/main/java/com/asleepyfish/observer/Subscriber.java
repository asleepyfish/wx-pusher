package com.asleepyfish.observer;

import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.enums.WxTemplateType;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/22 19:36
 * @Description: 订阅者
 */
public interface Subscriber {
    /**
     * 更新
     * 根据模板id接受信息显示
     *
     * @param wxTemplateType wx模板类型
     */
    void update(WxTemplateType wxTemplateType);


    /**
     * 得到身份信息
     *
     * @return {@link IdentityInfo}
     */
    IdentityInfo getIdentityInfo();
}
