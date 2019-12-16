package com.vcc.cem.mall.web.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author czh
 * @date 2019-12-10
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    NEW(0,""),
    FINISH(1,""),
    CANCEL(1,""),
    ;
    private Integer code;
    private String messege;
}
