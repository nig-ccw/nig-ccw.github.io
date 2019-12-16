CREATE TABLE `product_info`
(
  `product_id`            varchar(32)   NOT NULL,
  `product_name`          varchar(64)   NOT NULL COMMENT '商品名称',
  `product_price`         decimal(8, 2) NOT NULL COMMENT '商品价格',
  `product_stock`         int(11)       NOT NULL COMMENT '库存',
  `product_description`   varchar(64)            DEFAULT NULL COMMENT '商品描述',
  `product_icon`          varchar(512)           DEFAULT NULL COMMENT '商品小图',
  `product_category_code` int(11)       NOT NULL COMMENT '商品类目编号',
  `create_time`           timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
  `update_time`           timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='商品信息表';

CREATE TABLE `product_category`
(
  `product_category_id`   int         NOT NULL auto_increment,
  `product_category_name` varchar(64) NOT NULL COMMENT '商品类目名称',
  `product_category_code` int(11)     NOT NULL COMMENT '商品类目编号',
  `create_time`           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
  `update_time`           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`product_category_id`),
  unique key `que_product_category_code` (`product_category_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='商品类目表';

CREATE TABLE `order_master`
(
  `order_id`      varchar(32)   NOT NULL,
  `buyer_name`    varchar(32)   NOT NULL COMMENT '买家名称',
  `buyer_phone`   varchar(32)   NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128)  NOT NULL COMMENT '买家地址',
  `buyer_openid`  varchar(64)   NOT NULL COMMENT '买家微信openid',
  `order_amount`  decimal(8, 2) NOT NULL COMMENT '订单总金额',
  `order_status`  tinyint(3)    NOT NULL DEFAULT '0' COMMENT '订单状态，默认 0 - 新下单',
  `pay_status`    tinyint(3)    NOT NULL DEFAULT '0' COMMENT '订单支付状态，默认 0 - 未支付',
  `create_time`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
  `update_time`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  key `idx_buyer_openid` (`buyer_openid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单表';

CREATE TABLE `order_detail`
(
  `order_detail_id`  varchar(32)   NOT NULL,
  `order_id`         varchar(32)   NOT NULL,
  `product_id`       varchar(32)   NOT NULL,
  `product_name`     varchar(64)   NOT NULL COMMENT '商品名称',
  `product_price`    decimal(8, 2) NOT NULL COMMENT '商品价格',
  `product_quantity` int           NOT NULL COMMENT '商品数量',
  `product_icon`     varchar(512)           DEFAULT NULL COMMENT '商品小图',
  `create_time`      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建数据',
  `update_time`      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_detail_id`),
  key `idx_order_id` (`order_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单详情表';