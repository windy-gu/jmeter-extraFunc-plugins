package org.tester.jmeter.functions.core;
import org.json.simple.JSONObject;
import java.net.URLEncoder;

public class UrlEncodeUtil {
    /**
     * Urlencode 根据入参返回encode后的参数
     * @author Windy
     * @date 2021-4-02 13:15
     */
    public static String urlencode(String api, String body) {
        String request_data  = BossAPIRequest.apiQueryData(api, body);
        String encode = URLEncoder.encode(request_data);
//        System.out.println("encode:" + encode);
        return encode;
    }

    public static void main(String[] args) {
        String temp = urlencode("https://boss-uat.lifekh.com/takeaway-delivery/boss/delivery-order/assign-rider/action.do","{\"orderCodes\":[\"1390962995715891200\"],\"riderId\":10004}");
        System.out.println("Test data:" +temp);
    }
}
