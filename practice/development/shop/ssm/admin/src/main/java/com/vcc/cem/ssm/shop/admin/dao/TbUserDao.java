package com.vcc.cem.ssm.shop.admin.dao;

import com.vcc.cem.ssm.shop.commons.persistence.BaseDao;
import com.vcc.cem.ssm.shop.domain.TbUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserDao extends BaseDao<TbUser> {
    /**
     * 根据邮箱查询用户信息
     *
     * @param email
     * @return
     */
    TbUser getByEmail(String email);
}
