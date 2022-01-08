package com.kuangstudy.weixinpay.common.pay.weixin.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码链接生成参数
 * ReportReqData<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月16日-下午2:56:41 <BR>
 *
 * @version 1.0.0
 */
public class ReportReqDataMch {
    private String appid;//公众账号ID  TODO 普通,服务商,必填
    private String sub_appid;//微信分配的子商户公众账号ID TODO 服务商,可不填
    private String mch_id;//商户号  TODO 普通,服务商,必填
    private String sub_mch_id;//子商户号 TODO 服务商,必填
    private String nonce_str;//随机字符串 TODO 普通,服务商,必填
    private String sign;// 签名 TODO 普通,服务商,必填
    private String transaction_id; //TODO 普通,服务商,必填
    private String sign_type; //TODO 普通,服务商,选填,目前只支持HMAC-SHA256
    private String out_order_no; //TODO 普通,服务商,必填
    private String receivers; //TODO 分账账单 普通,服务商,必填
    private String receiver; //TODO 绑定关系时,用于填写绑定关系必填
    private String description; // TODO 分账完结描述 当执行完结分账时, 必填

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppid() {
        return appid;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
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

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
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

    @Override
    public String toString() {
        return "ReportReqDataMch{" +
                "appid='" + appid + '\'' +
                ", sub_appid='" + sub_appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", sub_mch_id='" + sub_mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", receivers='" + receivers + '\'' +
                '}';
    }
}
