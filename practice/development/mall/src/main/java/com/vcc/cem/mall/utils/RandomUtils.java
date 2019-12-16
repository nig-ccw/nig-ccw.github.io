package com.vcc.cem.mall.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface RandomUtils {
    static String ofAlpha(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    static int ofNumeric(int count) {
        return Integer.valueOf(RandomStringUtils.randomNumeric(count));
    }

    static String ofNumericString(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    static String ofAlphanumeric(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
