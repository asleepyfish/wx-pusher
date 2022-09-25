package com.asleepyfish.observer;

import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.enums.WxInformType;
import com.asleepyfish.enums.WxTemplateType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/23 17:06
 * @Description: 微信订阅号发布
 */
@Component
@ConfigurationProperties(prefix = "wx")
public class WxPublisher implements Publisher {
    /**
     * 这里利用HashSet来去重，并且IdentifyInfo中只根据appId，appSecret，openId作为唯一标识，地址信息可传可不传
     */
    private final Set<Subscriber> subscribers = new HashSet<>();

    private List<String> specialOpenIdList;

    @Override
    public void attach(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void detach(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void inform(WxTemplateType wxTemplateType) {
        // 给所有人发送
        if (wxTemplateType.getInformType() == WxInformType.ALL) {
            subscribers.forEach(subscriber -> subscriber.update(wxTemplateType));
        } else if (wxTemplateType.getInformType() == WxInformType.SPECIAL) {
            // 给Special发送
            subscribers.forEach(subscriber -> {
                if (specialOpenIdList.contains(subscriber.getIdentityInfo().getOpenId())) {
                    subscriber.update(wxTemplateType);
                }
            });
        } else {
            // 给Other发送
            subscribers.forEach(subscriber -> {
                if (!specialOpenIdList.contains(subscriber.getIdentityInfo().getOpenId())) {
                    subscriber.update(wxTemplateType);
                }
            });
        }
    }

    /**
     * 更新地址信息
     *
     * @param subscriber 订阅者
     */
    public void update(Subscriber subscriber) {
        for (Subscriber before : subscribers) {
            if (before.equals(subscriber)) {
                // 新的地址信息
                IdentityInfo newInfo = subscriber.getIdentityInfo();
                // 旧的地址信息
                IdentityInfo oldInfo = before.getIdentityInfo();
                oldInfo.setLatitude(newInfo.getLatitude());
                oldInfo.setLongitude(newInfo.getLongitude());
                oldInfo.setPrecision(newInfo.getPrecision());
                oldInfo.setAddress(newInfo.getAddress());
                oldInfo.setCountry(newInfo.getCountry());
                oldInfo.setProvince(newInfo.getProvince());
                oldInfo.setCity(newInfo.getCity());
                oldInfo.setDistrict(newInfo.getDistrict());
                return;
            }
        }
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSpecialOpenIdList(List<String> specialOpenIdList) {
        this.specialOpenIdList = specialOpenIdList;
    }
}
