package com.kuangstudy.weixinpay.common.pay.weixin.request;


import com.kuangstudy.weixinpay.common.pay.weixin.ReportReqData;
import com.kuangstudy.weixinpay.common.pay.weixin.config.WeiXinConstants;
import com.kuangstudy.weixinpay.common.pay.weixin.sign.Signature;
import com.kuangstudy.weixinpay.common.pay.weixin.util.WeiXinRequest;
import com.kuangstudy.weixinpay.common.pay.weixin.util.XMLParser;
import com.kuangstudy.weixinpay.config.properties.WeixinPayProperties;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据提交并且生成二维码
 * WeixinSubmit<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月16日-下午2:14:47 <BR>
 *
 * @version 1.0.0
 */
@Log4j2
@Component
public class QrCodeRequest {

    @Autowired
    private WeixinPayProperties weixinPayProperties;
    /**
     * 微信支付成功信息配置
     * 方法名：submitWeixinMessage<br/>
     * 创建人：mofeng <br/>
     * 时间：2018年11月28日-下午2:06:06 <br/>
     * 手机:1564545646464<br/>
     *
     * @param data
     * @return Map<String, String><br/>
     * @throws <br/>
     * @since 1.0.0<br />
     */
    public Map<String, String> submitWeixinMessage(ReportReqData data) {
        try {
            data.setTrade_type(data.getTrade_type());//扫一扫支付方式 NATIVE
            data.setSign(Signature.getSign(data.toMap(), weixinPayProperties.getAppsecret()));//签名字符
            // 发起支付请求
            String returnData = WeiXinRequest.submitData(weixinPayProperties.getGateway(), data.toMap());
            // returnData返回的是xml格式
            System.out.println("======================>" + returnData);
            if (StringUtils.isNotEmpty(returnData)) {
                log.info("当前支付成功返回的数据: ============>{}",returnData);
                //解析返回的字符串 并且组成map集合
                Map<String, String> map = XMLParser.getMapFromXML(returnData);
                System.out.println("==============================>" + map);
                if (null != map && !map.isEmpty()) {
                    String resultCode = (String) map.get(WeiXinConstants.RESULT);
                    if ("SUCCESS".equals(resultCode)) {//链接生成成功
                        HashMap<String, String> nmap = new HashMap<>();
                        String params = data.getAttach().replace("'", "\"");
                        nmap.put("appId", data.getAppid());
                        nmap.put("timeStamp", System.currentTimeMillis() + "");
                        nmap.put("signType", "MD5");
                        nmap.put("nonceStr", data.getNonce_str());
                        nmap.put("package", "prepay_id=" + map.get("prepay_id"));
                        nmap.put("paySign", Signature.getSign(nmap, weixinPayProperties.getAppsecret()));
                        nmap.put("attach",params);
                        nmap.put("orderNumber", data.getOut_trade_no() + "");
                        return nmap;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
