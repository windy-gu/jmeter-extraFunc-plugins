package org.apache.jmeter.visualizers.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author  Windy.Gu
 * @date    2022-07-14 18:00
 */
@Setter
@Getter
@ToString
public class ReportInfoVO {
    private String createTime;
    private String lastUpdateTime;
    private String toolName;
}
