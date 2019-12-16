package com.vcc.cem.api.tool.controller.weatherinfo;

import com.alibaba.fastjson.JSONObject;
import com.vcc.cem.api.tool.constant.Constant;
import com.vcc.cem.api.tool.controller.BaseController;
import com.vcc.cem.api.tool.dto.Result;
import com.vcc.cem.api.tool.dto.weatherinfo.GaodeWeatherInfoForecast;
import com.vcc.cem.api.tool.dto.weatherinfo.GaodeWeatherInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author czh
 * @date 2019-12-13
 */
@RestController
@RequestMapping(Constant.WEATHER_INFO)
public class WeatherInfoController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(Constant.WEATHER_INFO_MAPPING_GAODE)
    public List<GaodeWeatherInfoForecast> gaode(@PathVariable String city){
        Map<String,String> map = new HashMap<>();
        map.put(Constant.WEATHER_INFO_GAODE_MAP_KEY, porperties.getKeys().get(Constant.GAODE));
        map.put(Constant.WEATHER_INFO_GAODE_MAP_CITY, city);
        map.put(Constant.WEATHER_INFO_GAODE_MAP_EXTENSIONS, Constant.WEATHER_INFO_GAODE_MAP_EXTENSIONS_VALUE);
        String string=restTemplate.getForEntity(porperties.getWeatherinfo().get(Constant.GAODE), String.class, map).getBody();
        GaodeWeatherInfoResult result=JSONObject.parseObject(string, GaodeWeatherInfoResult.class);

        checkResult(result);

        return result.getForecasts();
    }
}
