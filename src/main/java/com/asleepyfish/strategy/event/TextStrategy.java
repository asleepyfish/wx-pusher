package com.asleepyfish.strategy.event;

import com.alibaba.fastjson2.JSONObject;
import com.asleepyfish.dto.AiQa;
import com.asleepyfish.enums.WxMessageType;
import com.asleepyfish.repository.AiQaRepository;
import com.asleepyfish.strategy.WxEventStrategy;
import com.google.common.collect.Lists;
import com.theokanning.openai.image.CreateImageRequest;
import io.github.asleepyfish.enums.ImageResponseFormatEnum;
import io.github.asleepyfish.enums.ImageSizeEnum;
import io.github.asleepyfish.util.OpenAiUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.Base64;
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

    @Resource
    private WxMpService wxMpService;

    @Override
    public void execute(Map<String, String> requestMap, HttpServletResponse response) throws Exception {
        // 发送方账号
        String openId = requestMap.get("FromUserName");
        String acceptContent = requestMap.get("Content");
        log.info(">>> 用户输入：{}", acceptContent);
        // 关闭输出流，避免微信服务端重复发送信息
        response.getOutputStream().close();
        if (acceptContent.charAt(0) == '/') {
            createImage(acceptContent, openId);
        } else {
            createCompletion(acceptContent, openId);
        }
    }

    private void createCompletion(String acceptContent, String openId) throws Exception {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setToUser(openId);
        wxMpKefuMessage.setMsgType(WxMessageType.TEXT.getType());
        List<String> results = Lists.newArrayList();
        // 初始化标记status = 0，表示解答成功
        int status = 0;
        try {
            results = OpenAiUtils.createCompletion(acceptContent, openId);
        } catch (Exception e) {
            status = -1;
            log.error(e.getMessage());
            results.add(e.getMessage());
        }
        for (String result : results) {
            if (result.startsWith("?") || result.startsWith("？")) {
                result = result.substring(1);
            }
            result = result.trim();
            wxMpKefuMessage.setContent(result);
            log.info(">>> ChatGPT：{}", result);
            AiQa aiQa = new AiQa();
            aiQa.setUser(openId);
            aiQa.setQuestion(acceptContent);
            aiQa.setAnswer(result);
            aiQa.setStatus(status);
            aiQaRepository.save(aiQa);
            // 客服接口发送信息
            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        }
    }

    private void createImage(String acceptContent, String openId) throws Exception {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setToUser(openId);
        wxMpKefuMessage.setMsgType(WxMessageType.IMAGE.getType());
        List<String> results = Lists.newArrayList();
        // 初始化标记status = 0，表示解答成功
        int status = 0;
        try {
            acceptContent = acceptContent.substring(1);
            results = OpenAiUtils.createImage(CreateImageRequest.builder()
                    .prompt(acceptContent)
                    .size(ImageSizeEnum.S512x512.getSize())
                    .user(openId)
                    .responseFormat(ImageResponseFormatEnum.B64_JSON.getResponseFormat())
                    .build());
        } catch (Exception e) {
            status = -1;
            log.error(e.getMessage());
            results.add(e.getMessage());
        }
        for (String result : results) {
            AiQa aiQa = new AiQa();
            aiQa.setUser(openId);
            aiQa.setQuestion(acceptContent);
            aiQa.setAnswer(result);
            aiQa.setStatus(status);
            aiQaRepository.save(aiQa);
            if (status == -1) {
                wxMpKefuMessage.setMsgType(WxMessageType.TEXT.getType());
                wxMpKefuMessage.setContent("生成图片失败！原因：" + result);
                wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
                return;
            }
            WxMediaUploadResult wxMediaUploadResult = getMediaUploadResult(result);
            log.info(">>> 图片上传结果：{}", JSONObject.toJSONString(wxMediaUploadResult));
            wxMpKefuMessage.setMediaId(wxMediaUploadResult.getMediaId());
            // 客服接口发送信息
            wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
        }
    }

    private WxMediaUploadResult getMediaUploadResult(String base64) throws Exception {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            return wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, "PNG", bis);
        }
    }
}
