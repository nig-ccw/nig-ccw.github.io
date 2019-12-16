package com.vcc.cem.mall.repository;

import com.vcc.cem.mall.entity.ProductInfo;
import com.vcc.cem.mall.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author czh
 * @date 2019-12-10
 */
@SpringBootTest
class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Test
    void findOne(){
        productInfoRepository.findById("1234567");
    }

    @Test
    void save(){
        ProductInfo productInfo=ObjectUtils.generate(ProductInfo.class);
        productInfoRepository.save(productInfo);
    }
}