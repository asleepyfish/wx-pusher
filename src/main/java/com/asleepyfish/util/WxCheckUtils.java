package com.asleepyfish.util;

import com.asleepyfish.common.WxConstants;
import org.apache.tomcat.util.buf.HexUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/24 10:40
 * @Description: 检查工具类
 */
public class WxCheckUtils {
    private WxCheckUtils(){}

    /**
     * 验证签名
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] infos = {WxConstants.TOKEN, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(infos);
        StringBuilder content = new StringBuilder();
        for (String info : infos) {
            content.append(info);
        }
        String key = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            // 将加密后的byte数组转化成十六进制字符串
            key = HexUtils.toHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return key != null && key.equals(signature);
    }
}
