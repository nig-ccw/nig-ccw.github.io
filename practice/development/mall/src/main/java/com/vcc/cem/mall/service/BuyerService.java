package com.vcc.cem.mall.service;

import com.vcc.cem.mall.service.dto.OrderDTO;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface BuyerService {
    OrderDTO findOrderOne(String openid, String orderId);
    OrderDTO cancelOrder(String openid, String orderId);
}
