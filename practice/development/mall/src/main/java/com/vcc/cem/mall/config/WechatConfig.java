//package com.vcc.cem.mall.config;
//
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
//import me.chanjar.weixin.mp.config.WxMpConfigStorage;
//import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
//import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author czh
// * @date 2019-12-10
// */
//@Configuration
//public class WechatConfig {
//
//    @Autowired
//    private WechatAccountProperties wechatAccountProperties;
//
//    @Bean
//    public WxMpService wxMpService(){
//        WxMpServiceImpl wxMpService=new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
//        return wxMpService;
//    }
//
//    @Bean
//    public WxMpConfigStorage wxMpConfigStorage(){
//        WxMpDefaultConfigImpl defaultConfig=new WxMpDefaultConfigImpl();
//        defaultConfig.setAppId(wechatAccountProperties.getMpAppId());
//        defaultConfig.setSecret(wechatAccountProperties.getMpAppSecret());
//        return defaultConfig;
//    }
//}
