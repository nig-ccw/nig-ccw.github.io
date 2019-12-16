package com.vcc.cem.mall.web.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author czh
 * @date 2019-12-10
 */
@Getter
@AllArgsConstructor
public enum OrderPayStatusEnum {
    WAIT(0,"未支付"),
    SUCCESS(1,"已支付"),
    ;

    private Integer code;
    private String messege;
}
