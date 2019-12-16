package com.vcc.cem.mall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * CREATE TABLE `product_info` (
 *   `product_id` varchar(32) NOT NULL,
 *   `product_name` varchar(64) NOT NULL COMMENT '商品名称',
 *   `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
 *   `product_stock` int(11) NOT NULL COMMENT '库存',
 *   `product_description` varchar(64) DEFAULT NULL COMMENT '商品描述',
 *   `product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
 *   `product_category_code` int(11) NOT NULL COMMENT '商品类目编号',
 *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
 *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 *   PRIMARY KEY (`product_id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';
 * @author czh
 * @date 2019-12-10
 */
@Entity
@Data
@NoArgsConstructor
public class ProductInfo {
    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    private Integer productStatus;
    private Integer productCategoryCode;
}
