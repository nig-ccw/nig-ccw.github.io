package com.vcc.cem.api.tool.controller.weatherinfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author czh
 * @date 2019-12-13
 */
@SpringBootTest
class WeatherInfoControllerTest {

    @Autowired
    private WeatherInfoController weatherInfoController;

    @Test
    void gaode() {
        System.out.println(weatherInfoController.gaode("110101"));
    }
}