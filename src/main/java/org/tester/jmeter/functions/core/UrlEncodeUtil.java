package org.tester.jmeter.functions.core;
import java.net.URLEncoder;

public class UrlEncodeUtil {
    /**
     * Urlencode 根据入参返回encode后的参数
     * @author Windy
     * @date 2021-4-02 13:15
     */
    public static String urlencode(String json) {
        String encode = URLEncoder.encode(json);
//        System.out.println("encode:" + encode);
        return encode;
    }

    public static void main(String[] args) {
        String temp = urlencode("{\"apiUrl\":\"https://boss-uat.lifekh.com/takeaway-delivery/boss/delivery-order/assign-rider/action.do\",\"apiData\":{\"orderCodes\":[\"1390962995715891200\"],\"riderId\":10004}}");
        System.out.println("encode:" +temp);
    }
}
