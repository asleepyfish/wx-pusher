package com.asleepyfish.controller;

import com.asleepyfish.service.WxService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-15 00:09
 * @Description: WxMpController
 */
@RestController
public class WxController {
    @Resource
    private WxService wxService;

    @PostMapping("/deleteCustomMenu")
    public void deleteCustomMenu(String menuJson) {
        wxService.deleteCustomMenu(menuJson);
    }
}
