package com.vcc.cem.ssm.shop.admin.service.dao;

import com.vcc.cem.ssm.shop.commons.persistence.BaseDao;
import com.vcc.cem.ssm.shop.domain.TbContent;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentDao extends BaseDao<TbContent> {
    /**
     * 根据类目 ID 删除内容
     *
     * @param categoryIds
     */
    void deleteByCategoryId(String[] categoryIds);
}
