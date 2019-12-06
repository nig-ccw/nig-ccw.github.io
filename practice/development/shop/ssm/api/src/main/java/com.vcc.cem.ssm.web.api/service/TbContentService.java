package com.vcc.cem.ssm.web.api.service;


import com.vcc.cem.ssm.shop.domain.TbContent;
import java.util.List;

public interface TbContentService {
    /**
     * 根据类别 ID 查询内容列表
     * @param categoryId
     * @return
     */
    List<TbContent> selectByCategoryId(Long categoryId);
}
