package com.vcc.cem.mall.service.impl;

import com.vcc.cem.mall.service.BuyerService;
import com.vcc.cem.mall.service.OrderService;
import com.vcc.cem.mall.service.dto.OrderDTO;
import com.vcc.cem.mall.service.utils.CheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author czh
 * @date 2019-12-10
 */
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO=orderService.findOne(orderId);
        if (Objects.isNull(orderDTO)) {
            return null;
        }
        CheckUtils.checkFindOrder(orderDTO, openid, log);
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=orderService.findOne(orderId);
        if (Objects.isNull(orderDTO)) {
            return null;
        }
        CheckUtils.checkCancelOrder(orderDTO, openid,orderId, log);

        return orderService.cancel(orderDTO);
    }
}
