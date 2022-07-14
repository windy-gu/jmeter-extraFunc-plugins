package org.apache.jmeter.visualizers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.jmeter.common.json.JsonUtil;

/**
 * Author: Windy.Gu
 * Date: 2022/7/14 18:18
 */
@Setter
@Getter
@ToString
public class TestStepVO {
    private String id;
    private Boolean status;
    private String title;
    private String description;
    private String reqHeader;
    private String request;
    private String resHeader;
    private String response;
    private String elapsedTime;

    private transient Long startTimestamp;

    public TestStepVO() {
        status = true;
    }

    /**
     * 设置TestCaseStep为测试通过
     */
    public void pass() {
        status = true;
    }

    /**
     * 设置TestCaseStep为测试失败
     */
    public void fail() {
        status = false;
    }

    public void setResponse(String value) {
        try {
            response = JsonUtil.prettyJson(value);
        } catch (Exception e) {
            response = value;
        }
    }

}
