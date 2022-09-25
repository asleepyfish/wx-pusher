package com.asleepyfish.strategy.template;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.asleepyfish.common.WxConstants;
import com.asleepyfish.common.WxTemplateConstants;
import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.strategy.WxTemplateStrategy;
import com.asleepyfish.util.WxOpUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-06 22:31
 * @Description: 普通早安消息推送策略
 */
@Service(WxTemplateConstants.COMMON_MORNING)
@Slf4j
public class CommonMorningStrategy implements WxTemplateStrategy {

    @Override
    public void execute(WxMpTemplateMessage wxMpTemplateMessage, IdentityInfo identityInfo) {
        Integer districtCode = WxOpUtils.getDistrictCode(identityInfo);
        // 获取天气的url
        String weatherUrl = "https://api.map.baidu.com/weather/v1/?district_id=" + districtCode + "&data_type=all&ak=" + WxConstants.BAI_DU_AK;
        // 天气信息json格式
        String weatherStr = HttpUtil.get(weatherUrl);
        JSONObject result = JSONObject.parseObject(JSONObject.parseObject(weatherStr).get("result").toString());
        // 实时天气
        JSONObject now = JSONObject.parseObject(result.get("now").toString());
        // 今日天气
        JSONObject today = JSONArray.parseArray(result.get("forecasts").toString()).getJSONObject(0);
        // 明日天气
        JSONObject tomorrow = JSONArray.parseArray(result.get("forecasts").toString()).getJSONObject(1);
        // 每日英语
        String dailyEnglishUrl = "http://api.tianapi.com/everyday/index?key=" + WxConstants.TX_AK;
        String dailyEnglishStr = HttpUtil.get(dailyEnglishUrl);
        JSONObject dailyEnglishObject = JSONArray.parseArray(JSONObject.parseObject(dailyEnglishStr).get("newslist").toString()).getJSONObject(0);
        // 英文句子
        String english = dailyEnglishObject.get("content").toString();
        // 中文翻译
        String chinese = dailyEnglishObject.get("note").toString();
        wxMpTemplateMessage.addData(new WxMpTemplateData("location", identityInfo.getAddress(), "#9370DB"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("now_temp", now.get("temp").toString(), "#87CEFA"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("now_weather", now.get("text").toString(), "#87CEEB"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("now_wind_dir", now.get("wind_dir").toString(), "#708090"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("now_wind_class", now.get("wind_class").toString(), "#708090"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("now_rh", now.get("rh").toString(), "#778899"));
        String todayWeatherDay = today.get("text_day").toString();
        String todayWeatherNight = today.get("text_night").toString();
        if (todayWeatherDay.equals(todayWeatherNight)) {
            wxMpTemplateMessage.addData(new WxMpTemplateData("today_weather", todayWeatherDay, "#FFC1C1"));
        } else {
            wxMpTemplateMessage.addData(new WxMpTemplateData("today_weather", todayWeatherDay + "转" + todayWeatherNight, "#FFC1C1"));
        }
        wxMpTemplateMessage.addData(new WxMpTemplateData("today_high", today.get("high").toString(), "#CD9B9B"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("today_low", today.get("low").toString(), "#CD9B9B"));
        String tomorrowWeatherDay = tomorrow.get("text_day").toString();
        String tomorrowWeatherNight = tomorrow.get("text_night").toString();
        if (tomorrowWeatherDay.equals(tomorrowWeatherNight)) {
            wxMpTemplateMessage.addData(new WxMpTemplateData("tomorrow_weather", tomorrowWeatherDay, "#DDA0DD"));
        } else {
            wxMpTemplateMessage.addData(new WxMpTemplateData("tomorrow_weather", tomorrowWeatherDay + "转" + tomorrowWeatherNight, "#DDA0DD"));
        }
        wxMpTemplateMessage.addData(new WxMpTemplateData("tomorrow_high", tomorrow.get("high").toString(), "#EE82EE"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("tomorrow_low", tomorrow.get("low").toString(), "#EE82EE"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("daily_english_en", english, "#FFCCFF"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("daily_english_cn", chinese, "#CCCCFF"));
    }
}
