package com.vcc.cem.mall.web.form;

import com.vcc.cem.mall.service.dto.OrderDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author czh
 * @date 2019-12-10
 */
@Data
public class OrderForm {
    @NotEmpty(message="姓名必填")
    private String name;
    @NotEmpty(message="手机号码必填")
    private String phone;
    @NotEmpty(message="地址必填")
    private String address;
    @NotEmpty(message="openid必填")
    private String openid;
    @NotEmpty(message="购物车不能为空")
    private String items;

    public OrderDTO convert(){
       return new OrderDTO(name, phone, address, openid, items);
    }
}

