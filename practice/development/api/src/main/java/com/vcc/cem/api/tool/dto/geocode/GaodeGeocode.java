package com.vcc.cem.api.tool.dto.geocode;

import lombok.Data;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class GaodeGeocode {
    private String formatted_address;//结构化地址信息 省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
    private String country;//国家
    private String province;//地址所在的省份名
    private String city;//地址所在的城市名
    private String adcode;//区域编码
    private String citycode;//城市编码
    private String district;//地址所在的区
    private String street;//街道
    private String number;//门牌
    private String location;//坐标点
    private String level;//匹配级别
}
