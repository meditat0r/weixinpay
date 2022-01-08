package com.kuangstudy.weixinpay.common.pay.weixin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码链接生成参数
 * ReportReqData<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月16日-下午2:56:41 <BR>
 * 注意：千万不要使用驼峰，到时候可能引发一些奇葩的问题和支付失败
 *
 * @version 1.0.0
 */
public class ReportReqData  {
    private String appid;//公众账号ID
    private String mch_id;//商户号
    private String nonce_str;//随机字符串
    private String sign;//签名
    private String body;//商品描述
    private String attach;//附加字段
    private String out_trade_no;//商户订单号
    private String total_fee;//金额
    private String spbill_create_ip;//终端ID
    private String sub_mch_id;//sub_mch_id
    private String notify_url;//通知地址
    private String trade_type;//交易类型  NATIVE--原生扫码支付 JSAPI 小程序支付  APP 为移动APP支付
    private String product_id;//商品ID
    private String device_info;
    private String openid;//小程序使用
    private String key;
    private String profit_sharing; //是否需要分账,Y分账,N不分账

    public String getProfit_sharing() {
        return profit_sharing;
    }

    public void setProfit_sharing(String profit_sharing) {
        this.profit_sharing = profit_sharing;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), String.valueOf(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
