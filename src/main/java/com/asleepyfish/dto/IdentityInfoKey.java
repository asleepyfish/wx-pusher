package com.asleepyfish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-09 00:08
 * @Description: 身份信息的主键
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdentityInfoKey implements Serializable {
    @Id
    @Column(name = "app_id")
    private String appId;

    @Id
    @Column(name = "app_secret")
    private String appSecret;

    @Id
    @Column(name = "open_id")
    private String openId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentityInfoKey that = (IdentityInfoKey) o;
        return Objects.equals(appId, that.appId) && Objects.equals(appSecret, that.appSecret) && Objects.equals(openId, that.openId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, appSecret, openId);
    }
}
