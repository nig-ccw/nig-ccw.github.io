package com.vcc.cem.mall.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface MapUtils {
    static <K,V> Map<K,V> of(List<K> keyList,List<V> valueList){
        Map<K,V> map=new HashMap<>();
        keyList.forEach(key->map.put(key,valueList.get(keyList.indexOf(key))));
        return map;
    }
}
