package com.vcc.cem.mall.service.impl;

import com.vcc.cem.mall.entity.ProductInfo;
import com.vcc.cem.mall.repository.ProductInfoRepository;
import com.vcc.cem.mall.service.ProductInfoService;
import com.vcc.cem.mall.service.dto.CartDTO;
import com.vcc.cem.mall.service.utils.CheckUtils;
import com.vcc.cem.mall.web.utils.enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author czh
 * @date 2019-12-10
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return null;
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.forEach(cartDTO->{
            ProductInfo productInfo=findOne(cartDTO.getProductId());
            CheckUtils.check(productInfo);

            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());
            save(productInfo);
        });
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.forEach(cartDTO->{
            ProductInfo productInfo=findOne(cartDTO.getProductId());
            CheckUtils.check(productInfo);

            Integer result=productInfo.getProductStock() - cartDTO.getProductQuantity();
            CheckUtils.check(result);

            productInfo.setProductStock(result);
            save(productInfo);
        });
    }
}
