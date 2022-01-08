package com.kuangstudy.weixinpay.common.pay.weixin.util;

import com.github.wxpay.sdk.WXPayUtil;
import com.kuangstudy.weixinpay.common.pay.weixin.enumtype.BusinessType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.util.Map;


/**
 * 请求微信生成扫一扫url
 * HttpsRequest<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月16日-下午5:05:36 <BR>
 *
 * @version 1.0.0
 */
@SuppressWarnings("all")
@Component
@Log4j2
public class WeiXinRequest {


    public static String submitData(String url, Object xmlObj) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

       // httpPost.set
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
        httpPost.abort();
        return result;
    }

    public static String submitData(String url, Map<String,String> xmlObj) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = null;
        postDataXML = WXPayUtil.mapToXml(xmlObj);
        log.info("xml===>"+postDataXML);

        // httpPost.set
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
        httpPost.abort();
        return result;
    }


    public static   String subMchData(String requestUrl, Map<String,String> xmlObj, String mch_id) throws Exception {
        URL url = new URL(requestUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        //设置请求方式
        conn.setRequestMethod("POST");
        //设置证书
        conn.setSSLSocketFactory(readCertificate(mch_id, BusinessType.MERCHANT));
        conn.setRequestProperty("content-type", "application/x-www-form-urkencoded");

         //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = null;
        postDataXML = WXPayUtil.mapToXml(xmlObj);
        System.out.println(postDataXML);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(postDataXML.getBytes("UTF-8"));
        outputStream.close();
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buff = new StringBuffer();
        while ((str=bufferedReader.readLine())!=null){
            buff.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream = null;
        conn.disconnect();
        return buff.toString();
    }


    /**
     * 根据类型区分分账证书
     * @param requestUrl
     * @param xmlObj
     * @param mch_id
     * @param type
     * @return
     * @throws Exception
     */
    public static   String subMchData(String requestUrl, Map<String,String> xmlObj, String mch_id,BusinessType type) throws Exception {
        URL url = new URL(requestUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        //设置请求方式
        conn.setRequestMethod("POST");
        //设置证书
        conn.setSSLSocketFactory(readCertificate(mch_id,type));
        conn.setRequestProperty("content-type", "application/x-www-form-urkencoded");

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = null;
        postDataXML = WXPayUtil.mapToXml(xmlObj);
        System.out.println(postDataXML);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(postDataXML.getBytes("UTF-8"));
        outputStream.close();
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buff = new StringBuffer();
        while ((str=bufferedReader.readLine())!=null){
            buff.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream = null;
        conn.disconnect();
        return buff.toString();
    }




    public static SSLSocketFactory readCertificate(String mchId, BusinessType type){
      try {
          KeyStore keyStore = KeyStore.getInstance("PKCS12");
          File file = new File("/apiclient_cert.p12");
          if (BusinessType.CLIENT.equals(type)) {
              file = ResourceUtils.getFile("/apiclient_cert_client.p12");
          } else if (BusinessType.DISTRIBUTOR.equals(type)) {
              file = ResourceUtils.getFile("/apiclient_cert_distributor.p12");
          }else if (BusinessType.MERCHANT.equals(type)){
              file = ResourceUtils.getFile("/apiclient_cert_merchant.p12");
          }else {
              String filePath = Class.class.getResource("/").getPath() + "/apiclient_cert_merchant.p12";
              file = new File(filePath);
          }
          log.info("读取到的文件路径cp: {}",file.getAbsolutePath());
          InputStream stream = new FileInputStream(file);
          keyStore.load(stream,mchId.toCharArray());
          stream.close();
          SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
          return sslContext.getSocketFactory();
      }catch (Exception e){
          log.info("证书双向认证失败: {}",e);
      }
      return null;
    }


}
