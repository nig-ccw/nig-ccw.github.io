package com.vcc.cem.ssm.shop.web.ui.api;

import com.vcc.cem.ssm.shop.commons.util.HttpClient;
import com.vcc.cem.ssm.shop.commons.util.Mapper;
import com.vcc.cem.ssm.shop.web.ui.dto.TbContent;
import org.apache.http.client.utils.HttpClientUtils;

import java.util.List;

/**
 * 内容管理接口
 * <p>Title: ContentsApi</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2018/7/4 14:57
 */
public class ContentsApi {

    /**
     * 请求幻灯片
     *
     * @return
     */
    public static List<TbContent> ppt() {
        List<TbContent> tbContents = null;
        String result = HttpClient.doGet(API.API_CONTENTS_PPT);
        try {
            tbContents = Mapper.json2listByTree(result, "data", TbContent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbContents;
    }
}
