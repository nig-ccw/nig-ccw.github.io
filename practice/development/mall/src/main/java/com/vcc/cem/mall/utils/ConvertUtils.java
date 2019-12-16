package com.vcc.cem.mall.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ConvertUtils {
    static <T, S> T convert(S s, Class<T> clz) {
        T t=BeanUtils.instantiateClass(clz);
        BeanUtils.copyProperties(s, t);
        return t;
    }

    static <T, S> T convert(S s, T t) {
        BeanUtils.copyProperties(s, t);
        return t;
    }

    static <T, S> List<T> convert(List<S> sList, Class<T> clz) {
        List<T> tList = new ArrayList<>();
        sList.forEach(s->tList.add(convert(s, clz)));
        return tList;
    }
}
