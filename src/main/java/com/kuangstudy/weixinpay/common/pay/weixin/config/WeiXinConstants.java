package com.kuangstudy.weixinpay.common.pay.weixin.config;

/**
 * 微信支付常量
 * WeiXinConstants<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月19日-下午7:14:15 <BR>
 *
 * @version 1.0.0
 */
public class WeiXinConstants {


    public static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一下单url

    public static final String MCH_URL = "https://api.mch.weixin.qq.com/secapi/pay/profitsharing";//统一分账url

    public static final String MCH_URL_MULTIPRO = "https://api.mch.weixin.qq.com/secapi/pay/multiprofitsharing";//统一分账url(多次分账)

    public static final String PROFIT_SHARING_QUERY = "https://api.mch.weixin.qq.com/pay/profitsharingquery"; //统一分账结果查询

    public static final String MCH_URL_FINISH = "https://api.mch.weixin.qq.com/secapi/pay/profitsharingfinish";//统一分账url

    public static final String MCH_STATUS_URL = "https://api.mch.weixin.qq.com/pay/profitsharingquery";//统一分账查询url

    public static final String MCH_ADD_RELATION_URL = "https://api.mch.weixin.qq.com/pay/profitsharingaddreceiver";//分账关系绑定地址

    public static final String REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";//退款


    public static final String RETURN_CODE = "return_code"; // SUCCESS/FAIL 此字段是通信标识，非交易标识

    public static final String SUCCESS = "SUCCESS"; // SUCCESS/FAIL 此字段是通信标识，非交易标识

    public static final int WIDTH = 200;//二维码宽度

    public static final int HEIGHT = 200;//二维码高度

    public static final String RESULT = "result_code";//返回结果

    public static final String ORDER_PAID = "orderPaid";//已经支付

    public static final String WEIXINPAY = "weixinPay";//可以支付

    public static final String CODE_URL = "code_url";//微信支付url

    public static final String ERROR_CODE = "err_code";//错误码

    public static final String ATTACH = "attach";//商家数据包

    public static final String BODY = "body";//商品名称

    public static final String NOTIFY_URL = "notify_url";//异步回调地址

    public static final String OUT_TRADE_NO = "out_trade_no";//商家订单号

    public static final String TRANSACTION_ID = "transaction_id";//微信订单号

    public static final String PRODUCT_ID = "product_id";//商品ID

    public static final String SPBILL_CREATE_IP = "spbill_create_ip";//支付用户IP地址

    public static final String TOTAL_FEE = "total_fee";//支付金额
}
