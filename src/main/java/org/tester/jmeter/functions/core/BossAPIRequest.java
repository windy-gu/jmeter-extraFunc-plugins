package org.tester.jmeter.functions.core;
import org.json.simple.JSONObject;


public class BossAPIRequest {
    /**
     * 实现boss api的调用
     * @author Windy
     * @date 2021-5-17 13:15
     */

    public static String apiQueryData(String api, String body) {
        String request_data = "{\"apiUrl\":\"" + api + "\",\"apiData\":" + body + "}";
        return request_data;
    }

    public static void main(String[] args) {
        JSONObject temp = new JSONObject();
        System.out.println("json:" +temp);
    }
}
