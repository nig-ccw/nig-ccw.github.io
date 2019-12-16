package com.vcc.cem.mall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * CREATE TABLE `order_detail` (
 *   `order_detail_id` varchar(32) NOT NULL,
 *   `order_id` varchar(32) NOT NULL,
 *   `product_id` varchar(32) NOT NULL,
 *   `product_name` varchar(64) NOT NULL COMMENT '商品名称',
 *   `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
 *   `product_quantity` int NOT NULL COMMENT '商品数量',
 *   `product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
 *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
 *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 *   PRIMARY KEY (`order_detail_id`),
 *   key `idx_order_id` (`order_id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';
 * @author czh
 * @date 2019-12-10
 */
@Entity
@Data
public class OrderDetail {
    @Id
    private String orderDetailId;
    private String orderId;
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private String productIcon;
    private Integer productQuantity;
}
