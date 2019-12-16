package com.vcc.cem.mall.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author czh
 * @date 2019-12-10
 */
@Data
@AllArgsConstructor
public class CartDTO {
    private String productId;
    private Integer productQuantity;
}
