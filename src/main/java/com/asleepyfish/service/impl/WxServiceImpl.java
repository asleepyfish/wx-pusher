package com.asleepyfish.service.impl;

import com.asleepyfish.service.WxService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-15 00:12
 * @Description: WxServiceImpl
 */
@Service
@Slf4j
public class WxServiceImpl implements WxService {
    @Resource
    private WxMpService wxMpService;

    @Override
    public void deleteCustomMenu(String menuJson) {
        try {
            if (StringUtils.isBlank(menuJson)) {
                wxMpService.getMenuService().menuDelete();
            } else {
                wxMpService.getMenuService().menuDelete(menuJson);
            }
            log.info(">>> 删除自定义菜单成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> 删除自定义菜单失败！");
        }
    }
}
