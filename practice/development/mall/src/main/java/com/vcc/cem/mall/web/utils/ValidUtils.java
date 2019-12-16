package com.vcc.cem.mall.web.utils;

import com.vcc.cem.mall.enums.ResultEnum;
import com.vcc.cem.mall.exception.MallException;
import com.vcc.cem.mall.service.dto.OrderDTO;
import com.vcc.cem.mall.web.form.OrderForm;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ValidUtils {
    static void validCreate(OrderForm orderForm, BindingResult bindingResult, Logger logger){
        if (bindingResult.hasErrors()) {
            logger.error("[创建订单] " + ResultEnum.PARAM_ERROR.getMessege() + "，orderForm={}", orderForm);
            throw new MallException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
    }

    static void validCreate(OrderDTO orderDTO, Logger logger){
        valid0(CollectionUtils.isEmpty(orderDTO.getOrderDetailList()), logger, "[创建订单] " + ResultEnum.CART_EMPTY.getMessege(), ResultEnum.CART_EMPTY);
    }

    static void validList(String openid, Logger logger){
        valid0(StringUtils.isEmpty(openid), logger, "[查询订单列表] openid 为空", ResultEnum.PARAM_ERROR);
    }

    static void validDetail(String openid, Logger logger){
        valid0(StringUtils.isEmpty(openid), logger, "[查询订单详情列表] openid 为空", ResultEnum.PARAM_ERROR);
    }

    static void validCancel(String openid, Logger logger){
        valid0(StringUtils.isEmpty(openid), logger, "[取消订单] openid 为空", ResultEnum.PARAM_ERROR);
    }

    static void valid0(Boolean b,Logger logger, String msg, ResultEnum resultEnum){
        if (b) {
            logger.error(msg);
            throw new MallException(resultEnum);
        }
    }
}
