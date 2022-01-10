package com.kuangstudy.weixinpay.controller;

import com.google.gson.Gson;
import com.kuangstudy.weixinpay.anno.IgnoreToken;
import com.kuangstudy.weixinpay.common.exception.KuangShenException;
import com.kuangstudy.weixinpay.common.pay.HttpClientUtils;
import com.kuangstudy.weixinpay.common.pay.weixin.request.QrCodeRequest;
import com.kuangstudy.weixinpay.vo.R;
import com.kuangstudy.weixinpay.config.properties.WeixinLoginProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author 徐柯
 * @Title:
 * @Package
 * @Description:
 * @date 2021/4/2214:13
 */
@RestController
@Log4j2
public class ApiLoginController extends ApiBaseController {

    /***
     * @Author xuke
     * @Description openid接口的获取
     * @Date 22:22 2021/4/24
     * @Param [code]
     * @return com.kuangstudy.weixinpay.vo.R
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
     *
    **/
    @IgnoreToken
    @GetMapping("/wxlogin")
    public R callback(String code) {
        // 1：判断code是否合法
        if (!StringUtils.hasText(code)) {
            throw new KuangShenException(22008, "登录失败，尝试刷新重新扫码登录!");
        }

        // 2：通过code获取access_token
        // 完成的连接： https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        // 参考地址：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
        String baseAccessTokenUrl = WeixinLoginProperties.WX_OPEN_GATEWAY +
                "?appid=%s" +
                "&secret=%s" +
                "&js_code=%s" +
                "&grant_type=authorization_code";

        log.info("1：appid:{},appscrect:{}", WeixinLoginProperties.WX_OPEN_APP_ID, WeixinLoginProperties.WX_OPEN_APP_SECRET);

        String accessTokenUrl = String.format(baseAccessTokenUrl, WeixinLoginProperties.WX_OPEN_APP_ID, WeixinLoginProperties.WX_OPEN_APP_SECRET, code);
        String result = null;
        try {
            //2：执行请求，获取微信请求返回得数据
            result = new HttpClientUtils().get(accessTokenUrl);
            log.info("2---->微信返回的日志信息是：code:{},result：{}", code, result);
            // 3： 对微信返回得数据进行转换
            Gson gson = new Gson();
            Map<String, Object> resultMap = gson.fromJson(result, HashMap.class);
            log.info("3---->微信返回的日志信息是：code:{}，resultMap：{}", code, resultMap);
            if (resultMap.get("errcode") != null) {
                throw new KuangShenException(22006, "微信登录出错!");
            }

            // 4: 解析微信用户得唯一凭证openid
            String openid = (String) resultMap.get("openid");
            if (!StringUtils.hasText(openid)) {
                throw new KuangShenException(22009, "登录失败，尝试刷新重新扫码登录!!!");
            }

            // 5：封装返回
            return R.ok().data("openid", openid).data("resultMap",resultMap);
        } catch (Exception e) {
            return R.error().code(601).message("微信解析失败");
        }
    }
}
