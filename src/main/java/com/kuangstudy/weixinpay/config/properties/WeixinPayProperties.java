package com.kuangstudy.weixinpay.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 获取配置文件中的相关信息
 */
@Configuration
@ConfigurationProperties(prefix = "weixin.pay.info")
@Data
public class WeixinPayProperties {
    /**定义微信解密获取手机号码的接口地址，固定的*/
    private String gateway;
    /**小程序APPID*/
    private String appid;
    /**小程序APPID*/
    private String type;
    /**小程序APPSceret*/
    private String appsecret;
    /**商户ID*/
    private String mchid;
    /**回调地址*/
    private String notifyPath;
}

