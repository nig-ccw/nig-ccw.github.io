package com.vcc.cem.ssm.shop.web.ui.api;

import com.vcc.cem.ssm.shop.commons.util.HttpClient;
import com.vcc.cem.ssm.shop.commons.util.Mapper;
import com.vcc.cem.ssm.shop.web.ui.dto.TbUser;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员管理接口
 * <p>Title: UsersApi</p>
 * <p>Description: </p>
 *
 * @author ccw
 * @version 1.0.0
 * @date 2018/7/6 9:41
 */
public class UsersApi {

    /**
     * 登录
     * @param tbUser
     * @return
     */
    public static TbUser login(TbUser tbUser) throws Exception {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", tbUser.getUsername()));
        params.add(new BasicNameValuePair("password", tbUser.getPassword()));

        String json = HttpClient.doPost(API.API_USERS_LOGIN, params.toArray(new BasicNameValuePair[params.size()]));
        TbUser user = Mapper.json2pojoByTree(json, "data", TbUser.class);
        return user;
    }
}
