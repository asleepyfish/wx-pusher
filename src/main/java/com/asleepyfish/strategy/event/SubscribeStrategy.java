package com.asleepyfish.strategy.event;

import com.asleepyfish.common.WxConstants;
import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.dto.IdentityInfoKey;
import com.asleepyfish.dto.ResponseMessage;
import com.asleepyfish.enums.WxMessageType;
import com.asleepyfish.observer.WxPublisher;
import com.asleepyfish.observer.WxSubscriber;
import com.asleepyfish.repository.IdentityInfoRepository;
import com.asleepyfish.strategy.WxEventStrategy;
import com.asleepyfish.util.WxOpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/31 19:26
 * @Description: 订阅活动对应策略
 */
@Service("subscribe")
@Slf4j
public class SubscribeStrategy implements WxEventStrategy {
    @Resource
    private WxPublisher wxPublisher;

    @Resource
    private IdentityInfoRepository identityInfoRepository;

    @Override
    public void execute(Map<String, String> requestMap, HttpServletResponse response) throws Exception {
        log.info(">>> 事件类型：subscribe");
        // 获取订阅者基本信息
        WxSubscriber wxSubscriber = WxOpUtils.getWxSubscriber(requestMap);
        log.info(">>> 新用户订阅！");
        // 加入日常推送列表
        wxPublisher.attach(wxSubscriber);
        // 用户关注后返回信息
        String returnContent = "感谢关注！" + System.lineSeparator() + "以/为开头的信息将被视作对应输入的图片请求~";
        // 发送方账号
        String openId = requestMap.get("FromUserName");
        // 公众号
        String publicId = requestMap.get("ToUserName");
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setFromUserName(publicId);
        responseMessage.setToUserName(openId);
        responseMessage.setMsgType(WxMessageType.TEXT.getType());
        responseMessage.setCreateTime(System.currentTimeMillis());
        responseMessage.setContent(returnContent);
        WxOpUtils.returnMessages(response, responseMessage);
        // 查询数据库里是否有该订阅者的信息
        IdentityInfoKey identityInfoKey = new IdentityInfoKey(WxConstants.APP_ID, WxConstants.APP_SECRET, openId);
        IdentityInfo infoFromDataBase = identityInfoRepository.findById(identityInfoKey).orElse(null);
        // 如果没有该订阅者信息则更新，有该订阅者信息则跳过（数据库中的信息可能包括更精确的经纬度信息）
        if (infoFromDataBase != null) {
            // 如果是取消关注重新关注的时候，需要更新status为0
            infoFromDataBase.setStatus(0);
            identityInfoRepository.save(infoFromDataBase);
            return;
        }
        IdentityInfo identityInfo = new IdentityInfo();
        identityInfo.setAppId(WxConstants.APP_ID);
        identityInfo.setAppSecret(WxConstants.APP_SECRET);
        identityInfo.setOpenId(openId);
        identityInfo.setPublicId(publicId);
        identityInfoRepository.save(identityInfo);
    }
}
