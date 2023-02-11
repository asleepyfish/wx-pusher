package com.asleepyfish.strategy.event;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.asleepyfish.common.WxConstants;
import com.asleepyfish.dto.AiQa;
import com.asleepyfish.enums.WxMessageType;
import com.asleepyfish.repository.AiQaRepository;
import com.asleepyfish.strategy.WxEventStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.asleepyfish.exception.ChatGPTException;
import io.github.asleepyfish.util.OpenAiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/31 19:55
 * @Description: 消息策略
 */
@Service("text")
@Slf4j
public class TextStrategy implements WxEventStrategy {
    @Resource
    private AiQaRepository aiQaRepository;

    @Override
    public void execute(Map<String, String> requestMap, HttpServletResponse response) throws Exception {
        // 发送方账号
        String openId = requestMap.get("FromUserName");
        String acceptContent = requestMap.get("Content");
        log.info(">>> 用户输入：{}", acceptContent);
        // 关闭输出流，避免微信服务端重复发送信息
        response.getOutputStream().close();
        Map<String, Object> responseMap = Maps.newHashMap();
        responseMap.put("touser", openId);
        responseMap.put("msgtype", WxMessageType.TEXT.getType());
        List<String> results = Lists.newArrayList();
        // 初始化标记status = 0，表示解答成功
        Integer status = 0;
        try {
            results = OpenAiUtils.createCompletion(acceptContent, openId);
        } catch (ChatGPTException e) {
            status = -1;
            log.error(e.getErrorMessage());
            results.add(e.getErrorMessage());
        }
        for (String result : results) {
            if (result.startsWith("?") || result.startsWith("？")) {
                result = result.substring(1);
            }
            result = result.trim();
            AiQa aiQa = new AiQa();
            aiQa.setUser(openId);
            aiQa.setQuestion(acceptContent);
            aiQa.setAnswer(result);
            aiQa.setStatus(status);
            aiQaRepository.save(aiQa);
            Map<String, Object> textMap = Maps.newHashMap();
            textMap.put("content", result);
            responseMap.put("text", textMap);
            log.info(">>> ChatGPT：{}", result);
            // 调用客服接口返回解答
            HttpUtil.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + WxConstants.accessToken, JSONObject.toJSONString(responseMap));
        }
    }
}
