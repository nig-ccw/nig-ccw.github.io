package com.vcc.cem.mall.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * CREATE TABLE `product_category` (
 *   `product_category_id` int NOT NULL auto_increment,
 *   `product_category_name` varchar(64) NOT NULL COMMENT '商品类目名称',
 *   `product_category_code` int(11) NOT NULL COMMENT '商品类目编号',
 *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
 *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 *   PRIMARY KEY (`product_category_id`),
 *   unique key `que_product_category_code` (`product_category_code`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类目表';
 * @author czh
 * @date 2019-12-10
 */
@Entity
@Data
@DynamicUpdate
public class ProductCategory {
    @Id
    private Integer productCategoryId;
    private String productCategoryName;
    private Integer productCategoryCode;
    private Date createTime;
    private Date updateTime;
}
