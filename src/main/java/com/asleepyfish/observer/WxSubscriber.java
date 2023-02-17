package com.asleepyfish.observer;

import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.enums.WxTemplateType;
import com.asleepyfish.strategy.WxTemplateContext;
import com.asleepyfish.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Objects;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/23 18:40
 * @Description: 微信订阅号订阅者
 * 重写一下hash和equals，身份信息相同的subscriber默认是一个订阅者
 * 本类由于需要不同订阅者信息，无法直接使用Spring托管，获取Spring注入对象使用SpringUtils中的方法
 */
@Slf4j
public class WxSubscriber implements Subscriber {
    /**
     * 身份信息
     */
    private final IdentityInfo identityInfo;

    public WxSubscriber(IdentityInfo identityInfo) {
        this.identityInfo = identityInfo;
    }

    @Override
    public void update(WxTemplateType wxTemplateType) {
        try {
            WxMpService wxMpService = SpringUtils.getBean(WxMpService.class);
            // 配置模板信息
            WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
            // 发送的模板信息
            wxMpTemplateMessage.setTemplateId(wxTemplateType.getTemplateId());
            // 接收人
            wxMpTemplateMessage.setToUser(identityInfo.getOpenId());
            // 使用Spring工具类获取Bean
            WxTemplateContext wxTemplateContext = SpringUtils.getBean(WxTemplateContext.class);
            // 调用不同的模板策略
            wxTemplateContext.execute(wxTemplateType, wxMpTemplateMessage, identityInfo);
            // 打印模板内容
            log.info(">>> 模板内容：{}", wxMpTemplateMessage.toJson());
            // 发送
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> 给[{}]推送{}策略失败", identityInfo.getOpenId(), wxTemplateType.getTemplateDescription());
            log.error(e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "WxSubscriber{" +
                "identityInfo=" + identityInfo +
                '}';
    }

    @Override
    public IdentityInfo getIdentityInfo() {
        return identityInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WxSubscriber that = (WxSubscriber) o;
        return Objects.equals(identityInfo, that.identityInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityInfo);
    }
}
