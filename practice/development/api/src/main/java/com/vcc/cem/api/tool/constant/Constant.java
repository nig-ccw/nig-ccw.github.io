package com.vcc.cem.api.tool.constant;

/**
 * @author czh
 * @date 2019-12-13
 */
public interface Constant {
    //返回数据编码
    String GAODE_RESULT_CODE_OK="1";
    String GAODE_RESULT_CODE_FAIL="0";
    String GAODE="gaode";

    //天气查询
    String WEATHER_INFO="weatherinfo";
    String WEATHER_INFO_MAPPING_GAODE=GAODE + "/{city}";
    String WEATHER_INFO_GAODE_MAP_KEY="key";
    String WEATHER_INFO_GAODE_MAP_CITY="city";
    String WEATHER_INFO_GAODE_MAP_EXTENSIONS="extensions";
    String WEATHER_INFO_GAODE_MAP_EXTENSIONS_VALUE="all";

    //地理编码
    String GEOCODE="geocode";
    String GEOCODE_MAPPING_GAODE=GAODE + "/{address}";
    String GEOCODE_GAODE_MAP_KEY="key";
    String GEOCODE_GAODE_MAP_ADDRESS="address";
    String GEOCODE_GAODE_MAP_CITY="city";
}
