package org.tester.jmeter.functions.core;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
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
     * @date 2022-1-02 13:15
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
            // 将{}数据进行去除外部{}，不包括内部的{}，并进行拼接
            sortedUnSignDate.forEach((key, value)  -> traverse(orderedSB, key, value));
        }
        String orderedRequests = orderedSB.substring(0, orderedSB.length() - 1);

        System.out.println("UNEnsign data test:" + orderedRequests);

        if (StringUtils.isBlank(orderedRequests)) {
            return "";
        }

        byte[] bytes_1 = orderedRequests.getBytes(StandardCharsets.UTF_8);
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
        String testString = "{'biz_content': {'actualPayAmount': '100'}, 'charset': 'UTF-8', 'open_id': '1465498758416519170','app_id': '1629948250', 'service': 'calculate.reward.point', 'accessToken': '1481181630566023168',  'timestamp': '2022-01-25 14:53:32', 'version': '1.0'}";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALz8qZE7dSRqkcBZd1NbqK0+EOJuVoDBzvDBwIjDyOoP7C+Yoxn3C9Uao4y39IhrCHQK/NP552qpeD/jHR6PQ/R3np6B+O96xsoBDBlTpD3+fTuDwxmcaMk4qakKyho/R0grY0mw0m+omEaJfYMfu4rkK5d9cEFyHHuLNugq3g3jAgMBAAECgYEAuP73mjRCulAbttedKBssZdHAw3ZQ9R9CzIhNSVOl5AGMKRdYaX1cttGp0YDtPXDQyI9M6M/TiaS1Eozmn1iMohvnRc+fcvP9XoGz7Xr/HcVCqQBjIPKK7NJC+LDzdcylLaSpLopxi6GOAcCbtlDSa8V6+eDVLHQ37IR9oEYKOukCQQD2TJb7HR0oWcpfT+dOlbDNp++HWR8TWqkBnEbou/ZjVG6+OCpvpQ35n27dHLuz51tXJutd+2i2Fa12Vx3us2UvAkEAxG4y/st/j+O5iPoBw29x3+KOWupg3C1OS8+9J95lLLMd1OCRSJNDAByMQlmDPfewxC7yI+fUoXbuFVgv5bcdjQJBALa8vIgzYZ6+f9eXgRZdGYB8SMsy5EuHyDzZpgKm8ndf/YpEQbfzzhqWn7qNxvYDgVF4Hsjr7xSpoLlciWjA0SECQHnv2rI2y1IcUHGKmQukI/GSZ0Ji2ovzh/Yh2E9mjDHqYutiGG4QFHh+QEdz37fZCZ9PcTr+0A0HkhYn71vPh4UCQDlKvTjMx9iAZOKcwsadVVjbsn5yQ4tulgUjujnlI08upfG8QbmsXceeUQRTh3tWmWVHBhMwgGmTleTCozQ4Mwc=";
//        String data = "app_id=1629948250&biz_content={\"pointScenes\":\"ORDER\",\"businessOrderNo\":\"1485533637793103872\",\"pointValue\":\"2.0\"}&charset=UTF-8&open_id=1484158911681052674&service=add.point.value&timestamp=2020-09-07 16:07:50&version=1.0;";
//        System.out.println("UNEnsign data true:" + data);
        String temp = rsaSignByPrivateKey(testString, privateKey);
        System.out.println("Ensign data:" + temp);
    }
}
