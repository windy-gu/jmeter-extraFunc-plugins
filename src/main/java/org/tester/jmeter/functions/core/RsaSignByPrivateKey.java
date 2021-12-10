package org.tester.jmeter.functions.core;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.Signature;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.common.json.JsonUtil;


public class RsaSignByPrivateKey {
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    /**
     * RSA 私钥加签
     * @author Windy
     */

    public static String rsaSignByPrivateKey(String data, String privateKey) throws Exception {
        // 加签的类型
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        byte[] bytes = Base64.decodeBase64(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey privateKey_byte = keyFactory.generatePrivate(keySpec);
//        System.out.println("privateKey_byte data:" + privateKey_byte);
        signature.initSign(privateKey_byte);

        // 序列化，将json格式{"key":"value"} 转化为{key=value}
        Map<Object, Object> jsonUnSignDate = null;
        if (StringUtils.isNotBlank(data)){
            jsonUnSignDate = JsonUtil.fromJson(data, JsonUtil.mapType);
            System.out.println("jsonUnSignDate:" + jsonUnSignDate);
        }

        // 根据key值，从a~z顺序进行排序
        Map<Object, Object> sortedUnSignDate = sortMapByKey(jsonUnSignDate);
        System.out.println("sortedUnSignDate:" + sortedUnSignDate);
        StringBuffer orderedSB = new StringBuffer();
        if (sortedUnSignDate != null){
            sortedUnSignDate.forEach((key, value)  -> traverse(orderedSB, key, value));
        }
        String orderedRequests = orderedSB.substring(0, orderedSB.length() - 1);

        System.out.println("UNEnsign data test:" + orderedRequests);

        if (StringUtils.isBlank(orderedRequests)) {
            return "";
        }

        byte[] bytes_1 = orderedRequests.getBytes(CharEncoding.UTF_8);
        signature.update(bytes_1);
        byte[] signed = signature.sign();
        return Base64.encodeBase64String(signed);
    }

    /**
     * 递归遍历报文并排序，排序完成后拼接字段
     */
    @SuppressWarnings("unchecked")
    private static void traverse(StringBuffer sb, Object key, Object value) {
        if (value instanceof Map) {
            sb.append(key).append("=").append(traverseMap((Map<Object, Object>) value)).append("&");
        } else if (value instanceof List) {
            sb.append(key).append("=").append(traverseList((List<Object>) value)).append("&");
        } else {
            sb.append(key).append("=").append(value).append("&");
        }
    }

    /**
     * 递归遍历报文并排序，仅针对json中{}的操作，排序完成后拼接字段
     */
    @SuppressWarnings("unchecked")
    private static void traverseInsideMap(StringBuffer sb, Object key, Object value) {

        if (value instanceof Map) {
            sb.append(key).append("=").append(traverseMap((Map<Object, Object>) value)).append("&");
        } else if (value instanceof List) {
            sb.append(key).append("=").append(traverseList((List<Object>) value)).append("&");
        } else {
            sb.append("\"").append(key).append("\":\"").append(value).append("\",");
        }
    }

    /**
     * 排序 Map并拼接字段
     */
    private static String traverseMap(Map<Object, Object> map) {
        StringBuffer sb = new StringBuffer();
        if (MapUtils.isEmpty(map)) {
            return sb.append("{}").toString();
        }
        Map<Object, Object> sortedMap = sortMapByKey(map);
        if (MapUtils.isEmpty(sortedMap)) {
            return sb.append("{}").toString();
        }
        sb.append("{");
        sortedMap.forEach((key, value) -> traverseInsideMap(sb, key, value));
        return sb.substring(0, sb.length() - 1) + "}";
    }

    /**
     * 排序 List并拼接字段
     */
    @SuppressWarnings("unchecked")
    private static String traverseList(List<Object> list) {
        StringBuffer sb = new StringBuffer();
        if (CollectionUtils.isEmpty(list)) {
            return sb.append("[]").toString();
        }
        sb.append("[");
        list.forEach(item -> {
            if (item instanceof Map) {
                sb.append(traverseMap((Map<Object, Object>) item));
            } else if (item instanceof List) {
                sb.append(traverseList((List<Object>) item));
            } else {
                sb.append(item);
            }
            sb.append(",");
        });
        return sb.substring(0, sb.length() - 1) + "]";
    }

    private static Map<Object, Object> sortMapByKey(Map<Object, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        // 降序排序
        Map<Object, Object> sortMap = new TreeMap<>(Comparator.comparing(Object::toString));
        sortMap.putAll(map);
        return sortMap;
    }


    public static void main(String[] args) throws Exception {
        String testString = "{'biz_content': {'mobile': '855010145010', 'couponNo': 'WNJ210908154513449'}, 'app_id': '1624010948354', 'charset': 'UTF-8', 'service': 'send.choice.coupon', 'timestamp': '2020-09-07 16:07:50', 'version': '1.0'}";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKfUj13CGKkxEtcmCg3IdQBMyXV46Cdwf+dLX1bmtaAkbPY5ov1zQrNaLICl+tc+zi6L8tqtjA3WS3P9YZiI9FMSCo8lImPrG1RTs7CM1Y6tzl8K/qsecQ44UPvEzlGRfGXttSqdmFYzsSWNIFidz6kXFszCBdYun7EDFnf7WDtXAgMBAAECgYBe/VLLqUDV+h2EwlXsaSm3qr5Xi8AyGl16JtHmWJwx8IvvbL3Qn7z/0CjiA4+O5lBCThl9Jb7gUgrQsnfboqBNues6XyCuiXypubI0sTh0UcrS0dZDSdqlJpGoIlsA2QnYfBEB5Czo+0E4b7+UVBr3F5pYVzu+0iyqcAcQwXUckQJBAPbkjcdYMdAprmis8r24DaX7qvKEDxunlSvW5kpJd+k3+toUpOQtpb7+rNOrlEJFU4SNjYfLgQzUby0AEYyMZJkCQQCuBWg+4lxFzud1tsGmeIu7sp7qwyoutYU2+KBTBvbSs0LD48kOBIDnIztJb8UOi14+ZydSxO32Ac2PiwY98qVvAkBAVjKz/cGNUy9Fy7u9wJad6EUVyV/+ft8ae3eraBW9Sn8uES8e3t5QNSFoT0/lLRekdRaqildotnr6KQhprbQRAkEAnpPs2Akcfry57Wn588I7y3JNIK9yXBgr6dkM+DwLZhvWxn1ndK+j630OhLAmiUd1PTZw/hrYoeoosRrGOGNKXwJBAK6YuCYRZsNd6LNbM/9iTkdJi5LoKwY4mPxegjlAXczdUIx3ylIzF+b36K6Zrr/b/HwEsUYrCpQJr/U1dKBcZs0=";
        String data = "app_id=1624010948354&biz_content={\"couponNo\":\"WNJ210908154513449\",\"mobile\":\"855010145010\"}&charset=UTF-8&service=send.choice.coupon&timestamp=2020-09-07 16:07:50&version=1.0";
        System.out.println("UNEnsign data true:" + data);
        String temp = rsaSignByPrivateKey(testString, privateKey);
        System.out.println("Ensign data:" + temp);
    }
}
