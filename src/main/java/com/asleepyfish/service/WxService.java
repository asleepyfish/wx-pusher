package com.asleepyfish.service;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-15 00:12
 * @Description: WxService
 */
public interface WxService {
    /**
     * 删除自定义菜单
     *
     * @param menuJson 名称
     */
    void deleteCustomMenu(String menuJson);
}
