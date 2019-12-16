package com.vcc.cem.api.tool.controller.geocode;

import com.alibaba.fastjson.JSONObject;
import com.vcc.cem.api.tool.constant.Constant;
import com.vcc.cem.api.tool.controller.BaseController;
import com.vcc.cem.api.tool.dto.geocode.GaodeGeocode;
import com.vcc.cem.api.tool.dto.geocode.GaodeGeocodeResult;
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
@RequestMapping(Constant.GEOCODE)
public class GeocodeController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(Constant.GEOCODE_MAPPING_GAODE)
    public List<GaodeGeocode> gaode(@PathVariable String address){
        Map<String,String> map = new HashMap<>();
        map.put(Constant.GEOCODE_GAODE_MAP_KEY, porperties.getKeys().get(Constant.GAODE));
        map.put(Constant.GEOCODE_GAODE_MAP_ADDRESS, address);
        String string=restTemplate.getForEntity(porperties.getGeocode().get(Constant.GAODE), String.class, map).getBody();
        GaodeGeocodeResult result=JSONObject.parseObject(string, GaodeGeocodeResult.class);

        checkResult(result);

        return result.getGeocodes();
    }
}
