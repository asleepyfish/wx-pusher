package com.asleepyfish.config;

import com.asleepyfish.common.WxConstants;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-14 21:08
 * @Description: 微信相关配置
 */
@Configuration
public class WxConfig {
    @Bean
    public WxMpService wxMpServiceImpl() {
        WxMpService wxMpService = new WxMpServiceImpl();
        // 配置基本信息
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(WxConstants.APP_ID);
        wxMpDefaultConfig.setSecret(WxConstants.APP_SECRET);
        // 设置基本信息
        wxMpService.setWxMpConfigStorage(wxMpDefaultConfig);
        return wxMpService;
    }
}
