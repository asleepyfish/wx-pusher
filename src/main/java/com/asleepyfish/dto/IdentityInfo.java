package com.asleepyfish.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Author: asleepyfish
 * @Date: 2022/8/27 11:02
 * @Description: 身份信息
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "identity_info", schema = "main")
@IdClass(IdentityInfoKey.class)
public class IdentityInfo {
    @Id
    @Column(name = "app_id")
    private String appId;

    @Id
    @Column(name = "app_secret")
    private String appSecret;

    @Id
    @Column(name = "open_id")
    private String openId;

    /**
     * 公众号
     */
    @Basic
    @Column(name = "public_id")
    private String publicId;

    /**
     * 纬度
     */
    @Basic
    @Column(name = "latitude")
    private String latitude;

    /**
     * 经度
     */
    @Basic
    @Column(name = "longitude")
    private String longitude;

    /**
     * 准确度
     */
    @Basic
    @Column(name = "precision")
    private String precision;

    /**
     * 国家
     */
    @Basic
    @Column(name = "country")
    private String country;

    /**
     * 省
     */
    @Basic
    @Column(name = "province")
    private String province;

    /**
     * 市
     */
    @Basic
    @Column(name = "city")
    private String city;

    /**
     * 区
     */
    @Basic
    @Column(name = "district")
    private String district;

    /**
     * 详细地址
     */
    @Basic
    @Column(name = "address")
    private String address;

    /**
     * 0表示正常关注，1表示取消关注
     */
    @Basic
    @Column(name = "status")
    private Integer status = 0;

    /**
     * 重写equals和hashcode，appId，appSecret和openId作为唯一标识
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentityInfo that = (IdentityInfo) o;
        return Objects.equals(appId, that.appId) && Objects.equals(appSecret, that.appSecret) && Objects.equals(openId, that.openId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, appSecret, openId);
    }
}
