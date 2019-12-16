package com.vcc.cem.mall.service;

import com.vcc.cem.mall.entity.ProductCategory;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer productCategoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByProductCategoryCodeIn(List<Integer> productCategoryCodeList);

    ProductCategory save(ProductCategory productCategory);

}
