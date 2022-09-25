package com.asleepyfish.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.asleepyfish.common.WxConstants;
import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.dto.IdentityInfoKey;
import com.asleepyfish.observer.WxPublisher;
import com.asleepyfish.observer.WxSubscriber;
import com.asleepyfish.repository.IdentityInfoRepository;
import com.asleepyfish.util.WxOpUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/29 19:12
 * @Description: 初始化需要获取的一些数据
 */
@RestController
public class InitController {
    @Resource
    private WxPublisher wxPublisher;

    @Resource
    private IdentityInfoRepository identityInfoRepository;

    /**
     * 初始化从远端拉取关注列表，比从数据库里拿更好
     */
    @PostConstruct
    public void init() {
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
}
