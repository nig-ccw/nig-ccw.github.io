package com.vcc.cem.api.tool.dto.weatherinfo;

import lombok.Data;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class GaodeWeatherInfoLive {
    private String province;//省份名
    private String city;//城市名
    private String adcode;//区域编码
    private String weather;//天气现象
    private String temperature;//实时气温，单位：摄氏度
    private String winddirection;//风向描述
    private String windpower;//风力级别，单位：级
    private String humidity;//空气湿度
    private String reporttime;//数据发布的时间
}
