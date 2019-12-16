package com.vcc.cem.api.tool.dto.weatherinfo;

import com.vcc.cem.api.tool.dto.Result;
import lombok.Data;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class GaodeWeatherInfoResult extends Result {
    private String infocode;//返回状态说明,10000代表正确
    private List<GaodeWeatherInfoLive> lives;//实况天气数据信息
    private List<GaodeWeatherInfoForecast> forecasts;//预报天气信息数据
}
