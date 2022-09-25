package com.asleepyfish.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Author: asleepyfish
 * @Date: 2022-09-08 23:58
 * @Description: 全国各个地区的districtCode、经纬度信息
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "weather_district_id", schema = "main")
public class DistrictInfo {
    @Id
    @Column(name = "area_code")
    private Integer areaCode;

    @Basic
    @Column(name = "district_code")
    private Integer districtCode;

    @Basic
    @Column(name = "city_geocode")
    private Integer cityGeocode;

    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "district_geocode")
    private Integer districtGeocode;

    @Basic
    @Column(name = "district")
    private String district;

    @Basic
    @Column(name = "lon")
    private String lon;

    @Basic
    @Column(name = "lat")
    private String lat;

    @Basic
    @Column(name = "sta_fc")
    private String staFc;

    @Basic
    @Column(name = "sta_rt")
    private String staRt;

    @Basic
    @Column(name = "province")
    private String province;

    @Basic
    @Column(name = "fc_lon")
    private String fcLon;

    @Basic
    @Column(name = "fc_lat")
    private String fcLat;

    @Basic
    @Column(name = "rt_lon")
    private String rtLon;

    @Basic
    @Column(name = "rt_lat")
    private String rtLat;

    @Basic
    @Column(name = "origin_areacode")
    private Integer originAreacode;

    @Basic
    @Column(name = "exclude")
    private Integer exclude;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DistrictInfo that = (DistrictInfo) o;
        return Objects.equals(areaCode, that.areaCode) && Objects.equals(districtCode, that.districtCode) && Objects.equals(cityGeocode, that.cityGeocode) && Objects.equals(city, that.city) && Objects.equals(districtGeocode, that.districtGeocode) && Objects.equals(district, that.district) && Objects.equals(lon, that.lon) && Objects.equals(lat, that.lat) && Objects.equals(staFc, that.staFc) && Objects.equals(staRt, that.staRt) && Objects.equals(province, that.province) && Objects.equals(fcLon, that.fcLon) && Objects.equals(fcLat, that.fcLat) && Objects.equals(rtLon, that.rtLon) && Objects.equals(rtLat, that.rtLat) && Objects.equals(originAreacode, that.originAreacode) && Objects.equals(exclude, that.exclude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, districtCode, cityGeocode, city, districtGeocode, district, lon, lat, staFc, staRt, province, fcLon, fcLat, rtLon, rtLat, originAreacode, exclude);
    }
}
