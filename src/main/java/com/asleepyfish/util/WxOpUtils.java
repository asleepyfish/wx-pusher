package com.asleepyfish.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.asleepyfish.common.WxConstants;
import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.dto.ResponseMessage;
import com.asleepyfish.enums.WxMessageType;
import com.asleepyfish.exception.WxException;
import com.asleepyfish.observer.WxSubscriber;
import com.asleepyfish.repository.DistrictInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: asleepyfish
 * @Date: 2022-08-31 23:00
 * @Description: 操作工具类
 */
@Slf4j
public class WxOpUtils {
    private WxOpUtils() {
    }

    public static void returnMessages(HttpServletResponse response, ResponseMessage responseMessage) throws IOException {
        try (PrintWriter printWriter = response.getWriter()) {
            // 转化成微信可接收的xml信息返回
            String transformedMessage = transformMessage(responseMessage);
            log.info(">>> reply message : {}", responseMessage.getContent());
            printWriter.println(transformedMessage);
        }
    }

    /**
     * 将信息转换成xml返回
     *
     * @param textMessage 存储回复信息的对象
     * @return 微信指定接受的回复信息格式（xml）
     */
    public static String transformMessage(ResponseMessage textMessage) {
        String responseMessage = "<xml>\n" +
                "  <ToUserName><![CDATA[" + textMessage.getToUserName() + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + textMessage.getFromUserName() + "]]></FromUserName>\n" +
                "  <CreateTime>" + System.currentTimeMillis() + "</CreateTime>\n" +
                "  <MsgType><![CDATA[" + textMessage.getMsgType() + "]]></MsgType>\n";
        if (textMessage.getMsgType().equals(WxMessageType.TEXT.getType())) {
            responseMessage = responseMessage + "  <Content><![CDATA[" + textMessage.getContent() + "]]></Content>\n";
        } else if (textMessage.getMsgType().equals(WxMessageType.TRANSFER_CUSTOMER_SERVICE.getType())) {
            responseMessage = responseMessage +
                    "  <TransInfo>\n" +
                    "    <KfAccount><![CDATA[" + WxConstants.CUSTOMER_SERVICE + "]]></KfAccount>\n" +
                    "  </TransInfo>\n";
        }
        return responseMessage + "</xml>";
    }

    /**
     * 获取访问令牌
     *
     * @return {@link String}
     */
    public static String getAccessToken() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
                "appid=" + WxConstants.APP_ID + "&secret=" + WxConstants.APP_SECRET;
        String accessTokenStr = HttpUtil.get(accessTokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(accessTokenStr);
        return jsonObject.get("access_token").toString();
    }

    /**
     * 得到wx订阅者
     *
     * @param requestMap 请求映射
     * @return {@link WxSubscriber}
     */
    public static WxSubscriber getWxSubscriber(Map<String, String> requestMap) {
        // 发送方账号
        String openId = requestMap.get("FromUserName");
        // 公众号
        String officialAccount = requestMap.get("ToUserName");
        IdentityInfo identityInfo = new IdentityInfo();
        identityInfo.setAppId(WxConstants.APP_ID);
        identityInfo.setAppSecret(WxConstants.APP_SECRET);
        identityInfo.setOpenId(openId);
        identityInfo.setPublicId(officialAccount);
        return new WxSubscriber(identityInfo);
    }

    /**
     * 复制属性不是空的字段
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyPropertiesNotNull(Object source, Object target) {
        // 获取非空字段拷贝，第三个参数获取的空字段用来排除
        BeanUtils.copyProperties(source, target, getIgnoreProperties(source));
    }

    /**
     * 得到空字段属性
     *
     * @param source 源
     * @return {@link String[]}
     */
    private static String[] getIgnoreProperties(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> properties = new HashSet<>();
        if (propertyDescriptors.length > 0) {
            for (PropertyDescriptor p : propertyDescriptors) {
                String name = p.getName();
                Object value = beanWrapper.getPropertyValue(name);
                if (value == null) {
                    properties.add(name);
                }
            }
        }
        return properties.toArray(new String[0]);
    }

    /**
     * 地区代码
     *
     * @param identityInfo 身份信息
     * @return {@link Integer}
     */
    public static Integer getDistrictCode(IdentityInfo identityInfo) {
        Integer districtCode;
        try {
            String district = identityInfo.getDistrict();
            String city = identityInfo.getCity();
            char suffix = district.charAt(district.length() - 1);
            if (suffix == '区' || suffix == '县') {
                district = district.substring(0, district.length() - 1);
            }
            if (city.charAt(city.length() - 1) == '市') {
                city = city.substring(0, city.length() - 1);
            }
            districtCode = SpringUtils.getBean(DistrictInfoRepository.class).getDistrictCode(city, district);
        } catch (Exception e) {
            throw new WxException("获取地区编码错误，请检查是否开启允许地理位置访问！");
        }
        return districtCode;
    }

    public static Long countDays(String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date begin = sdf.parse(beginDate);
            Date end = sdf.parse(endDate);
            return DateUtil.between(begin, end, DateUnit.DAY);
        } catch (ParseException e) {
            log.error("日期解析失败：{}", e.getMessage());
        }
        return -1L;
    }
}
