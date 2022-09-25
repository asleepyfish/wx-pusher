package com.asleepyfish.service.impl;

import com.asleepyfish.enums.WxStatusType;
import com.asleepyfish.service.ValidateService;
import com.asleepyfish.util.WxCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/24 19:00
 * @Description: 验证实现类
 */
@Service
@Slf4j
public class ValidateServiceImpl implements ValidateService {

    @Override
    public String checkToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 签名
            String signature = request.getParameter("signature");
            if (StringUtils.isBlank(signature)) {
                return WxStatusType.FAIL.getType();
            }
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            log.info("signature[{}], timestamp[{}], nonce[{}], echostr[{}]", signature, timestamp, nonce, echostr);
            if (WxCheckUtils.checkSignature(signature, timestamp, nonce)) {
                log.info("验证成功！echostr[{}]", echostr);
                return echostr;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return WxStatusType.FAIL.getType();
    }
}
