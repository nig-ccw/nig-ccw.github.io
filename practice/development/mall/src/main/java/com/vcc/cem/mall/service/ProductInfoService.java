package com.vcc.cem.mall.service;

import com.vcc.cem.mall.entity.ProductInfo;
import com.vcc.cem.mall.service.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ProductInfoService {
    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartList);

    void decreaseStock(List<CartDTO> cartList);
}
