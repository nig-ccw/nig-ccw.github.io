package com.vcc.cem.api.tool.dto;

import com.vcc.cem.api.tool.dto.weatherinfo.GaodeWeatherInfoForecast;
import com.vcc.cem.api.tool.dto.weatherinfo.GaodeWeatherInfoLive;
import lombok.Data;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-13
 */
@Data
public class Result {
    private String status;//返回状态
    private String count;//返回结果总数目
    private String info;//返回的状态信息
}
