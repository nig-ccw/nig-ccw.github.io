package com.vcc.cem.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author czh
 * @date 2019-12-10
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    PRODUCT_NOT_EXIST(0,"商品不存在"),
    PRODUCT_STOCK_ERROR(1,"库存不正确"),
    ORDER_NOT_EXIST(2,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(3,"订单详情不存在"),
    ORDER_DETAIL_EMPTY(4,"订单详情为空"),
    ORDER_STATUS_ERROR(5,"订单状态不正确"),
    ORDER_UPDATE_FAIL(6,"订单更新失败"),
    ORDER_PAY_STATUS_ERROR(7,"订单支付状态不正确"),
    PARAM_ERROR(8,"参数不正确"),
    CART_EMPTY(9,"购物车为空"),
    ORDER_OPEN_ID_ERROR(10,"订单openid不一致"),
    ORDER_ID_ERROR(11,"查不到该订单"),
    WECHAT_MP_ERROR(12,"微信公众账号错误"),
    ;
    private Integer code;
    private String messege;
}
