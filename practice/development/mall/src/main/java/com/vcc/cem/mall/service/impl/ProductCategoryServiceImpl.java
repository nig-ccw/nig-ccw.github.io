package com.vcc.cem.mall.service.impl;

import com.vcc.cem.mall.entity.ProductCategory;
import com.vcc.cem.mall.repository.ProductCategoryRepository;
import com.vcc.cem.mall.repository.ProductInfoRepository;
import com.vcc.cem.mall.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer productCategoryId) {
        return productCategoryRepository.findById(productCategoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByProductCategoryCodeIn(List<Integer> productCategoryTypeList) {
        return productCategoryRepository.findByProductCategoryCodeIn(productCategoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
