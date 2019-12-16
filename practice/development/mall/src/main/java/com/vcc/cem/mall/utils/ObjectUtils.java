package com.vcc.cem.mall.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ObjectUtils {
    List<Class<?>> INTEGER_CLS=Arrays.asList(short.class, Short.class, int.class, Integer.class, long.class, Long.class);
    List<Class<?>> FLOAT_CLS=Arrays.asList(float.class, Float.class, double.class, Double.class);

    static <T> T generate(Class<T> clz) {
        try {
            T t=BeanUtils.instantiateClass(clz);
            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> type=field.getType();
                if (INTEGER_CLS.contains(type)) {
                    field.set(t, 1);
                } else if (type.equals(BigDecimal.class)) {
                    field.set(t, new BigDecimal(RandomUtils.ofNumeric(2)));
                } else if (type.equals(BigInteger.class)) {
                    field.set(t, new BigInteger(RandomUtils.ofNumericString(20)));
                } else if (FLOAT_CLS.contains(type)) {
                    field.set(t, Double.valueOf(RandomUtils.ofNumeric(3)));
                } else if (type.equals(Date.class)) {
                    field.set(t, Date.from(Instant.from(LocalDateTime.now())));
                } else {
                    field.set(t, RandomUtils.ofAlpha(20));
                }
            }
            return t;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
