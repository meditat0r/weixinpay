package com.kuangstudy.weixinpay.config.properties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
@PropertySource("classpath:application.yml")
public class WeixinLoginProperties implements InitializingBean {
	@Value("${weixin.login.info.gateway}")
	private String gateway;
	@Value("${weixin.login.info.appid}")
	private String appid;
	@Value("${weixin.login.info.appsecret}")
	private String appsecret;
	@Value("${weixin.login.info.redirectUrl}")
	private String redirectUrl;
	public static String WX_OPEN_GATEWAY;
	public static String WX_OPEN_APP_ID;
	public static String WX_OPEN_APP_SECRET;
	public static String WX_OPEN_REDIRECT_URL;
	@Override
	public void afterPropertiesSet() throws Exception {
		WX_OPEN_APP_ID = appid;
		WX_OPEN_GATEWAY = gateway;
		WX_OPEN_APP_SECRET = appsecret;
		WX_OPEN_REDIRECT_URL = redirectUrl;
	}
}