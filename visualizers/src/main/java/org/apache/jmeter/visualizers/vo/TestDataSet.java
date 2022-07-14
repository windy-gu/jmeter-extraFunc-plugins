package org.apache.jmeter.visualizers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Windy.Gu
 * Date: 2022/7/14 18:23
 */

@Setter
@Getter
@ToString(exclude = "testSuiteMap")
public class TestDataSet {
    private ArrayList<TestSuiteVO> testSuiteList;

    private transient HashMap<String, TestSuiteVO> testSuiteMap;

    public TestDataSet() {
        testSuiteMap = new HashMap<>();
    }

    public void putTestSuite(TestSuiteVO testSuite) {
        testSuiteMap.put(testSuite.getTitle(), testSuite);
    }

    public TestSuiteVO getTestSuite(String title) {
        return testSuiteMap.get(title);
    }

    /**
     * map转list
     */
    public void setTestSuiteList() {
        testSuiteList = new ArrayList<>();
        testSuiteMap.keySet().forEach(key->testSuiteList.add(testSuiteMap.get(key)));
    }
}
