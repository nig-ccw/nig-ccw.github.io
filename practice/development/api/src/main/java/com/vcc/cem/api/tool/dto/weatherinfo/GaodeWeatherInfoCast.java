package com.vcc.cem.api.tool.dto.weatherinfo;

import lombok.Data;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class GaodeWeatherInfoCast {
    private String date;//日期
    private String week;//星期几
    private String dayweather;//白天天气现象
    private String nightweather;//晚上天气现象
    private String daytemp;//白天温度
    private String nighttemp;//晚上温度
    private String daywind;//白天风向
    private String nightwind;//晚上风向
    private String daypower;//白天风力
    private String nightpower;//晚上风力
}
