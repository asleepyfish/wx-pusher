package com.asleepyfish.strategy.event;

import com.asleepyfish.enums.WxStatusType;
import com.asleepyfish.strategy.WxEventStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: asleepyfish
 * @Date: 2022-08-31 23:37
 * @Description: 模板发送成功后的回调信息
 */
@Service("TEMPLATESENDJOBFINISH")
@Slf4j
public class TemplateSendJobFinishStrategy implements WxEventStrategy {
    @Override
    public void execute(Map<String, String> requestMap, HttpServletResponse response) throws Exception {
        log.info(">>> 事件类型：TEMPLATESENDJOBFINISH");
        String status = requestMap.get("Status");
        if (WxStatusType.SUCCESS.getType().equals(status)) {
            log.info(">>> 模板信息发送成功！");
        } else {
            log.info(">>> 模板信息发送失败！");
        }
    }
}
