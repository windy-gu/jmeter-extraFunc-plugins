package org.tester.jmeter.functions.core;
import org.json.simple.JSONObject;
import java.net.URLEncoder;

public class UrlEncodeUtil {
    /**
     * Urlencode 根据入参返回encode后的参数
     * @author Windy
     * @date 2021-5-02 13:15
     */
    public static String urlencode(String api, String body) {
        String request_data  = apiQueryData(api, body);
        String encode = URLEncoder.encode(request_data);
        return encode;
    }

    public static String apiQueryData(String api, String body) {
        String request_data = "{\"apiUrl\":\"" + api + "\",\"apiData\":" + body + "}";
        return request_data;
    }

    public static void main(String[] args) {
        String temp = urlencode("https://boss-uat.lifekh.com/takeaway-delivery/boss/delivery-order/assign-rider/action.do","{\"loginName\":\"jmeter\",\"password\":\"45f33a4881ff5f7c1525c9447cde26fd\",\"captcha\":\"\"}}");
        System.out.println("Test data:" +temp);
    }
}
