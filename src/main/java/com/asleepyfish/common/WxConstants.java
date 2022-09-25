package com.asleepyfish.common;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/23 18:46
 * @Description: 常量类
 */
public final class WxConstants {
    private WxConstants() {
    }

    /**
     * appId
     */
    public static final String APP_ID = "";

    /**
     * appSecret
     */
    public static final String APP_SECRET = "";

    /**
     * 公众号
     */
    public static final String PUBLIC_ID = "";

    /**
     * token
     */
    public static final String TOKEN = "asleepyfish";

    /**
     * 请求api需要的token，开启定时任务，每一个小时获取一次
     */
    public static String accessToken = "";

    /**
     * 请求百度地图相关服务的AK
     */
    public static final String BAI_DU_AK = "";

    /**
     * 天行API AK
     */
    public static final String TX_AK = "";

    /**
     * 相识日期
     */
    public static final String MEET_DATE = "2018-03-02";
}
