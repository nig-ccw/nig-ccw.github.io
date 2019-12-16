package com.vcc.cem.mall.web.controller;

import com.vcc.cem.mall.entity.ProductCategory;
import com.vcc.cem.mall.entity.ProductInfo;
import com.vcc.cem.mall.service.ProductCategoryService;
import com.vcc.cem.mall.service.ProductInfoService;
import com.vcc.cem.mall.utils.ConvertUtils;
import com.vcc.cem.mall.web.utils.ResultVOUtils;
import com.vcc.cem.mall.web.vo.ProductInfoVO;
import com.vcc.cem.mall.web.vo.ProductVO;
import com.vcc.cem.mall.web.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author czh
 * @date 2019-12-10
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @GetMapping("list")
    public ResultVO<List<ProductVO>> list() {
        //1 查询所有上架商品
        List<ProductInfo> productInfoList=productInfoService.findUpAll();

        //2 一次查询所有类目
        List<Integer> productCategoryCodeList=productInfoList.stream().map(info -> info.getProductCategoryCode()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList=productCategoryService.findByProductCategoryCodeIn(productCategoryCodeList);

        //3 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();

        productCategoryList.forEach(category->{
            ProductVO productVO = ConvertUtils.convert(category, ProductVO.class);

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            productInfoList.stream()
                    .filter(info->info.getProductCategoryCode().equals(category.getProductCategoryCode()))
                    .forEach(info->{
                productInfoVOList.add(ConvertUtils.convert(info, ProductInfoVO.class));
            });
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        });

        //4 返回 ResultVO
        return ResultVOUtils.success(productVOList);
    }
}
