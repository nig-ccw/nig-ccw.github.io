package com.vcc.cem.ssm.shop.admin.service.service;


import com.vcc.cem.ssm.shop.commons.persistence.BaseService;
import com.vcc.cem.ssm.shop.domain.TbContent;

public interface TbContentService extends BaseService<TbContent> {
    /**
     * 根据类目 ID 删除内容
     * @param categoryIds
     */
    void deleteByCategoryId(String[] categoryIds);
}
