package com.asleepyfish.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.asleepyfish.common.WxConstants;
import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.dto.IdentityInfoKey;
import com.asleepyfish.observer.WxPublisher;
import com.asleepyfish.observer.WxSubscriber;
import com.asleepyfish.repository.IdentityInfoRepository;
import com.asleepyfish.service.InitService;
import com.asleepyfish.util.WxOpUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-14 22:45
 * @Description: InitServiceImpl
 */
@Service
@Slf4j
public class InitServiceImpl implements InitService {
    @Resource
    private WxPublisher wxPublisher;

    @Resource
    private IdentityInfoRepository identityInfoRepository;

    @Resource
    private WxMpService wxMpService;

    @Override
    public void initSubscriberList() {
        String accessToken = WxOpUtils.getAccessToken();
        // 获取关注者列表
        String openIdUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
        String openIdStr = HttpUtil.get(openIdUrl);
        Object data = JSONObject.parseObject(openIdStr).get("data");
        // 没有获取关注者直接return
        if (data == null) {
            return;
        }
        String openIdsStr = JSONObject.parseObject(data.toString()).get("openid").toString();
        List<String> openIdList = JSONArray.parseArray(openIdsStr).toJavaList(String.class);
        for (String openId : openIdList) {
            IdentityInfo identityInfo = new IdentityInfo();
            identityInfo.setAppId(WxConstants.APP_ID);
            identityInfo.setAppSecret(WxConstants.APP_SECRET);
            identityInfo.setOpenId(openId);
            identityInfo.setPublicId(WxConstants.PUBLIC_ID);
            // 查询表中是否有对应主键的身份信息
            IdentityInfoKey identityInfoKey = new IdentityInfoKey(WxConstants.APP_ID, WxConstants.APP_SECRET, openId);
            IdentityInfo infoFromDataBase = identityInfoRepository.findById(identityInfoKey).orElse(null);
            WxSubscriber wxSubscriber;
            if (infoFromDataBase == null) {
                identityInfoRepository.save(identityInfo);
                wxSubscriber = new WxSubscriber(identityInfo);
            } else {
                wxSubscriber = new WxSubscriber(infoFromDataBase);
            }
            wxPublisher.attach(wxSubscriber);
        }
    }

    @Override
    public void initCustomMenu() {
        try {
            // 创建WxMenu对象并设置菜单项
            WxMenu menu = new WxMenu();
            // 转换聊天模式按钮
            WxMenuButton switchChatModeButton = new WxMenuButton();
            switchChatModeButton.setName("转换聊天模式");
            switchChatModeButton.setType(WxConsts.MenuButtonType.CLICK);
            switchChatModeButton.setKey("SWITCH_CHAT_MODE");
            menu.getButtons().add(switchChatModeButton);

            // 调用WxMpService的menuCreate()方法，将创建的菜单上传到微信服务器
            wxMpService.getMenuService().menuCreate(menu);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(">>> 初始化自定义菜单异常！");
        }
    }
}
