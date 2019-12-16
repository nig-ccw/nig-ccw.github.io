package com.vcc.cem.api.tool.controller.geocode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author czh
 * @date 2019-12-13
 */
@SpringBootTest
class GeocodeControllerTest {
    @Autowired
    private GeocodeController geocodeController;

    @Test
    void gaode() {
        System.out.println(geocodeController.gaode("杭州市杭州经济技术开发区白杨街道2号大街1号"));
    }
}