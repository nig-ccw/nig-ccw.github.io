package com.vcc.cem.mall.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
@Data
public class ProductVO {
    @JsonProperty("name")
    private String productCategoryName;
    @JsonProperty("code")
    private Integer productCategoryCode;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
