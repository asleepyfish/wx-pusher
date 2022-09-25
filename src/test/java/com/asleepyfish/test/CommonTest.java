package com.asleepyfish.test;

import com.asleepyfish.dto.IdentityInfo;
import com.asleepyfish.observer.Subscriber;
import com.asleepyfish.observer.WxPublisher;
import com.asleepyfish.observer.WxSubscriber;
import org.junit.jupiter.api.Test;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-05 00:19
 * @Description: 普通测试
 */
public class CommonTest {

    /**
     * 只由appId，appSecret，openId作为唯一标识，修改了地址信息，也是同一对象
     */
    @Test
    public void subscribeTest() {
        IdentityInfo before = new IdentityInfo();
        before.setAppId("1");
        before.setAppSecret("1");
        before.setOpenId("1");
        WxSubscriber wxSubscriber = new WxSubscriber(before);
        WxPublisher wxPublisher = new WxPublisher();
        wxPublisher.attach(wxSubscriber);
        for (Subscriber subscriber : wxPublisher.getSubscribers()) {
            System.out.println(subscriber);
        }
        System.out.println("=======================");
        IdentityInfo after = new IdentityInfo();
        after.setAppId("1");
        after.setAppSecret("1");
        after.setOpenId("1");
        after.setLatitude("1");
        after.setLongitude("1");
        after.setPrecision("1");
        WxSubscriber wxSubscriber1 = new WxSubscriber(after);
        // 更新地址信息
        wxPublisher.update(wxSubscriber1);
        for (Subscriber subscriber : wxPublisher.getSubscribers()) {
            System.out.println(subscriber);
        }
    }

    @Test
    public void equalTest() {
        IdentityInfo e1 = new IdentityInfo();
        e1.setAppId("1");
        e1.setAppSecret("1");
        e1.setOpenId("1");
        WxSubscriber wxSubscriber = new WxSubscriber(e1);
        IdentityInfo e2 = new IdentityInfo();
        e2.setAppId("1");
        e2.setAppSecret("1");
        e2.setOpenId("1");
        e2.setLatitude("1");
        e2.setLongitude("1");
        e2.setPrecision("1");
        WxSubscriber wxSubscriber1 = new WxSubscriber(e2);
        System.out.println(wxSubscriber.equals(wxSubscriber1));
    }
}
