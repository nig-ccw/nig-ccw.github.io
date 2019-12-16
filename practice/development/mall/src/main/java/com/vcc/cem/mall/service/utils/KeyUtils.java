package com.vcc.cem.mall.service.utils;

import com.vcc.cem.mall.utils.RandomUtils;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface KeyUtils {
    static String generate(){
        return RandomUtils.ofNumericString(20);
    }
}
