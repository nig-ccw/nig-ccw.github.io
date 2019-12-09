package com.vcc.cem.ssm.shop.commons.util;

/**
 * 正则表达式工具类
 * <p>Title: TbContent</p>
 * <p>Description: </p>
 *
 * @author ccw
 * @version 1.0.0
 * @date 2018/6/25 14:02
 */
public interface Regex {
    String PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    String EMAIL = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";

}
