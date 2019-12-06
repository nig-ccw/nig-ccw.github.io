package com.vcc.cem.ssm.shop.admin.service.service;


import com.vcc.cem.ssm.shop.commons.persistence.BaseService;
import com.vcc.cem.ssm.shop.domain.TbUser;

public interface TbUserService extends BaseService<TbUser> {
    /**
     * 用户登录
     *
     * @param email
     * @param password
     * @return
     */
    TbUser login(String email, String password);
}
