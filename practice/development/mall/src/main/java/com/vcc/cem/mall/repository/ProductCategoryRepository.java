package com.vcc.cem.mall.repository;

import com.vcc.cem.mall.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findByProductCategoryCodeIn(List<Integer> productCategoryCodeList);
}
