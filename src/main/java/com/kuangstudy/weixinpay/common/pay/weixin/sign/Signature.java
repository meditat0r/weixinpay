package com.kuangstudy.weixinpay.common.pay.weixin.sign;

import com.kuangstudy.weixinpay.common.pay.weixin.config.WeiXinConfig;
import com.kuangstudy.weixinpay.common.pay.weixin.util.XMLParser;
import org.xml.sax.SAXException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 签名算法
 * Signature<BR>
 * 创建人:小威 <BR>
 * 时间：2015年10月16日-下午2:12:44 <BR>
 *
 * @version 1.0.0
 */
public class Signature {
    /**
     * 签名算法
     * (MD5加密map及key的信息)<BR>
     * 方法名：getSign<BR>
     * 创建人：小威 <BR>
     * 时间：2015年10月16日-下午2:12:55 <BR>
     *
     * @param
     * @return
     * @throws IllegalAccessException String<BR>
     * @throws <BR>
     * @since 1.0.0
     */
    public static String getSign(Map<String, String> map, String key) {
        ArrayList<String> list = new ArrayList(20);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        // 在compare中先按照字符串长度取短的那个字符串的长度作为条件
        // 然后循环判断两个字符串的第一个字符的ASCII码大小，做出递增排序
        // 如果两个字符串第一个字符的ASCII码一致，则判断第二个字符
        // 以此类推，通过这种方式将字符串通过首字母的ASCII码进行排序
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    public static String HMACSHA256(Map<String, String> map, String key) throws Exception {

        ArrayList<String> list = new ArrayList<String>(20);
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        System.out.println(result);

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(result.getBytes("UTF-8"));

        StringBuilder sbs = new StringBuilder();

        for (byte item : array) {

            sbs.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

        }

        return sbs.toString().toUpperCase();

    }

    public static String getSign(Map<String, String> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WeiXinConfig.KEY;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }


    /**
     * 从API返回的XML数据里面重新计算一次签名
     * 方法名：getSignFromResponseString<BR>
     * 创建人：小威 <BR>
     * 时间：2015年10月16日-下午2:11:53 <BR>
     *
     * @param responseString API返回的XML数据
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException String<BR>
     * @throws <BR>
     * @since 1.0.0
     */
    public static String getSignFromResponseString(String responseString) throws IOException, SAXException, ParserConfigurationException {
        Map<String, String> map = XMLParser.getMapFromXML(responseString);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return Signature.getSign(map);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * 方法名：checkIsSignValidFromResponseString<BR>
     * 创建人：小威 <BR>
     * 时间：2015年10月16日-下午2:12:19 <BR>
     *
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException                 boolean<BR>
     * @throws <BR>
     * @since 1.0.0
     */
    public static boolean checkIsSignValidFromResponseString(String responseString) throws ParserConfigurationException, IOException, SAXException {
        Map<String, String> map = XMLParser.getMapFromXML(responseString);
        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            return false;
        }
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getSign(map);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            return false;
        }
        return true;
    }

    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * 生成签名数据_HmacSHA1加密
     *
     * @param data
     *            待加密的数据
     * @param key
     *            加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String getSignature(String data, String key) throws Exception {

        byte[] keyBytes = key.getBytes();
        // 根据给定的字节数组构造一个密钥。
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(data.getBytes());

        String hexBytes = byte2hex(rawHmac);
        return hexBytes;
    }

    private static String byte2hex(final byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
            stmp = (Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }


}
