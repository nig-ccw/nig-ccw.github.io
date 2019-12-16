package com.vcc.cem.mall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CREATE TABLE `order_master` (
 *   `order_id` varchar(32) NOT NULL,
 *   `buyer_name` varchar(32) NOT NULL COMMENT '买家名称',
 *   `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
 *   `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
 *   `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
 *   `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
 *   `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态，默认 0 - 新下单',
 *   `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单支付状态，默认 0 - 未支付',
 *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
 *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 *   PRIMARY KEY (`order_id`),
 *   key `idx_buyer_openid` (`buyer_openid`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
 * @author czh
 * @date 2019-12-10
 */
@Entity
@Data
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    private Date createTime;
    private Date updateTime;

}
