package com.vcc.cem.api.tool.dto.weatherinfo;

import lombok.Data;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class GaodeWeatherInfoForecast {
    private String city;//城市名
    private String province;//省份名
    private String adcode;//区域编码
    private String reporttime;//预报发布时间
    private List<GaodeWeatherInfoCast> casts;//预报数据
}
