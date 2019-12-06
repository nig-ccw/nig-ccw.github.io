package com.vcc.cem.ssm.web.api.service;

import com.vcc.cem.ssm.shop.domain.TbUser;

/**
 * 会员管理
 * <p>Title: TbUserService</p>
 * <p>Description: </p>
 *
 * @author ccw
 * @version 1.0.0
 * @date 2018/7/6 9:12
 */
public interface TbUserService {

    /**
     * 登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);
}
