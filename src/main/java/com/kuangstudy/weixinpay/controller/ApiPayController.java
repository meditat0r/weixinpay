package com.kuangstudy.weixinpay.controller;

import com.alibaba.fastjson.JSONObject;
import com.kuangstudy.weixinpay.common.pay.weixin.ReportReqData;
import com.kuangstudy.weixinpay.common.pay.weixin.request.QrCodeRequest;
import com.kuangstudy.weixinpay.common.pay.weixin.util.RandomStringGenerator;
import com.kuangstudy.weixinpay.entity.Product;
import com.kuangstudy.weixinpay.service.ProductService;
import com.kuangstudy.weixinpay.vo.R;
import com.kuangstudy.weixinpay.config.properties.WeixinPayProperties;
import com.kuangstudy.weixinpay.utils.json.JsonUtil;
import com.kuangstudy.weixinpay.utils.snow.SnowflakeIdWorker;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 徐柯
 * @Title:
 * @Package
 * @Description:
 * @date 2021/4/2212:05
 */
@RestController
@Log4j2
public class ApiPayController extends  ApiBaseController {

    @Autowired
    private WeixinPayProperties weixinProperties;
    @Autowired
    private ProductService productService;
    @Autowired
    private QrCodeRequest qrCodeRequest;

    /**
     * @Author xuke
     * @Description 微信小程序支付的接口
     * @Date 10:39 2021/4/25
     * @Param [request]
     * @return com.kuangstudy.weixinpay.vo.R
    **/
    @GetMapping("/weixinpay")
    public R payVip(HttpServletRequest request) {
        //开始支付,创建数据体
        JSONObject json = new JSONObject();

        //业务数据
        String productId = request.getParameter("pid");
        if(StringUtils.isEmpty(productId)) {
            return R.error().code(601).message("产品没找到!");
        }

        // 1：根据产品id查询对应产品信息
        Product product = productService.getById(productId);
        if(product==null) {
            return R.error().code(601).message("产品没找到!");
        }

        // 2：生成订单号
        String orderNo = new SnowflakeIdWorker(1, 2).nextId() + "";
        String ip = "127.0.0.1";
        // 3：获取微信小程序的openid
        String openid = request.getParameter("openid");
        if(StringUtils.isEmpty(openid)) {
            return R.error().code(601).message("openid没找到!");
        }
        //4：分账接收方用户Id session或者redis
        Integer userId = 1;

        // 组装微信支付数据
        ReportReqData data = new ReportReqData();
        // openid
        data.setOpenid(openid);
        // 签约的类型JSAPI
        data.setTrade_type(weixinProperties.getType());
        // 微信小程序的appid
        data.setAppid(weixinProperties.getAppid());
        // 商户id
        data.setMch_id(weixinProperties.getMchid());
        // 回调地址 如果是微信小程序不用配置也可以，最好配置
        data.setNotify_url(weixinProperties.getNotifyPath());
        // 业务数据(套餐名称/订单号/商品ID/ip地址/金额/)
        data.setBody(product.getTitle());
        data.setOut_trade_no(orderNo);
        data.setProduct_id(productId+"");
        data.setSpbill_create_ip(ip);
        data.setTotal_fee(getMoney(product.getPrice()));
        data.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        json.put("userId", userId);
        json.put("type", "productpay");
        json.put("ip", ip);
        //创建订单
        String params = json.toString().replace("\"", "'");
        log.info("执行支付传递的参数: {}", params);
        data.setAttach(params);
        log.info("操作完毕,开始发起支付");

        //微信支付返回的结果
        Map<String, String> weixinMap = qrCodeRequest.submitWeixinMessage(data);

        return R.ok().data("weixinMap",weixinMap).data("xxx","xxxx");
    }

    /**
     * 元转换成分
     *
     * @param amount
     * @return
     */
    public static String getMoney(String amount) {
        if (amount == null) {
            return "";
        }
        // 金额转化为分为单位
        // 处理包含, ￥ 或者$的金额
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }
}
