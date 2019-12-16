package com.vcc.cem.mall.service.utils;

import com.vcc.cem.mall.entity.OrderMaster;
import com.vcc.cem.mall.entity.ProductInfo;
import com.vcc.cem.mall.service.dto.OrderDTO;
import com.vcc.cem.mall.enums.ResultEnum;
import com.vcc.cem.mall.exception.MallException;
import com.vcc.cem.mall.utils.MapUtils;
import com.vcc.cem.mall.web.utils.enums.OrderPayStatusEnum;
import com.vcc.cem.mall.web.utils.enums.OrderStatusEnum;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface CheckUtils {
    Map<Class<?>,ResultEnum> CHECK_MAP=MapUtils.of(
        Arrays.asList(
            ProductInfo.class,
            OrderMaster.class
        ),
        Arrays.asList(
            ResultEnum.PRODUCT_NOT_EXIST,
            ResultEnum.ORDER_NOT_EXIST
        )
    );


    static <T> void check(T t){
        if (Objects.isNull(t)) {
            throw new MallException(CHECK_MAP.get(t.getClass()));
        }
    }

    static void check(Integer t){
        if (t < 0) {
            throw new MallException(ResultEnum.PRODUCT_STOCK_ERROR);
        }
    }

    static <T> void checkList(List<T> tList){
        if (CollectionUtils.isEmpty(tList)) {
            throw new MallException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
    }

    static <T> void checkCancel(List<T> tList, OrderDTO orderDTO, Logger logger){
        if (CollectionUtils.isEmpty(tList)) {
            logger.error("[取消订单] " + ResultEnum.ORDER_DETAIL_NOT_EXIST.getMessege() + "，orderDTO={}", orderDTO);
            throw new MallException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
    }

    static void checkCancel(OrderDTO orderDTO, Logger logger){
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("[取消订单] " + ResultEnum.ORDER_STATUS_ERROR.getMessege() + "，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new MallException(ResultEnum.ORDER_STATUS_ERROR);
        }
    }

    static void checkCancel(OrderMaster result, OrderMaster orderMaster, Logger logger){
        if (Objects.isNull(result)) {
            logger.error("[取消订单] " + ResultEnum.ORDER_UPDATE_FAIL.getMessege() + "，orderMaster={}", orderMaster);
            throw new MallException(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

    static void checkFinish(OrderDTO orderDTO, Logger logger){
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("[完结订单] " + ResultEnum.ORDER_STATUS_ERROR.getMessege() + "，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new MallException(ResultEnum.ORDER_STATUS_ERROR);
        }
    }

    static void checkFinish(OrderMaster result, OrderMaster orderMaster, Logger logger){
        if (Objects.isNull(result)) {
            logger.error("[完结订单] " + ResultEnum.ORDER_UPDATE_FAIL.getMessege() + "，orderMaster={}", orderMaster);
            throw new MallException(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

    static void checkPaid(OrderDTO orderDTO, Logger logger){
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            logger.error("[订单支付完成] " + ResultEnum.ORDER_STATUS_ERROR.getMessege() + "，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new MallException(ResultEnum.ORDER_STATUS_ERROR);
        }
    }

    static void checkPaidStatus(OrderDTO orderDTO, Logger logger){
        if (!orderDTO.getPayStatus().equals(OrderPayStatusEnum.WAIT.getCode())) {
            logger.error("[订单支付完成] " + ResultEnum.ORDER_PAY_STATUS_ERROR.getMessege() + "，orderDTO{}", orderDTO);
            throw new MallException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
    }

    static void checkPaid(OrderMaster result, OrderMaster orderMaster, Logger logger){
        if (Objects.isNull(result)) {
            logger.error("[订单支付完成] " + ResultEnum.ORDER_UPDATE_FAIL.getMessege() + "，orderMaster={}", orderMaster);
            throw new MallException(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

    static void checkFindOrder(OrderDTO orderDTO, String openid, Logger logger){
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            logger.error("[查询订单] " + ResultEnum.ORDER_OPEN_ID_ERROE.getMessege() + "，openid={},orderDTO={}", openid, orderDTO);
            throw new MallException(ResultEnum.ORDER_OPEN_ID_ERROE);
        }
    }

    static void checkCancelOrder(OrderDTO orderDTO, String openid, String orderId, Logger logger){
        checkFindOrder(orderDTO, openid, logger);
        if (Objects.isNull(orderDTO)) {
            logger.error("[取消订单] " + ResultEnum.ORDER_ID_ERROE.getMessege() + "，orderId={}", orderId);
            throw new MallException(ResultEnum.ORDER_ID_ERROE);
        }
    }
}
