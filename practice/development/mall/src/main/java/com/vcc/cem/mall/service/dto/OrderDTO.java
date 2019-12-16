package com.vcc.cem.mall.service.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vcc.cem.mall.entity.OrderDetail;
import com.vcc.cem.mall.web.utils.serializer.Date2LongSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
@Data
@NoArgsConstructor
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    @JsonSerialize(using=Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using=Date2LongSerializer.class)
    private Date updateTime;
    private List<OrderDetail> orderDetailList;

    public OrderDTO(String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid, String items){
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderDetailList=JSON.parseArray(items, OrderDetail.class);
    }
}
