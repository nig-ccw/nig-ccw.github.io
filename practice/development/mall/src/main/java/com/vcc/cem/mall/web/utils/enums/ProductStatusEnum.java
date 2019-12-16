package com.vcc.cem.mall.web.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author czh
 * @date 2019-12-10
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架"),
    ;
    private Integer code;
    private String messege;

}
