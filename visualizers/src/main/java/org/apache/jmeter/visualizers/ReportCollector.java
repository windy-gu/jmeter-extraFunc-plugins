package org.apache.jmeter.visualizers;

import org.apache.jmeter.common.cli.CliOptions;
import org.apache.jmeter.common.utils.TimeUtil;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.engine.util.NoThreadClone;

import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.vo.TestCaseVO;
import org.apache.jmeter.visualizers.vo.TestStepVO;
import org.apache.jmeter.visualizers.vo.TestSuiteVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Windy.Gu
 */
public class ReportCollector extends AbstractTestElement
        implements TestStateListener, ThreadListener, SampleListener, Interruptible, NoThreadClone {

    private static final Logger log = LoggerFactory.getLogger(ReportCollector.class);

    public static final String DATA_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String REPORT_NAME = "ReportCollector.reportName";
    public static final String IS_APPEND = "ReportCollector.isAppend";

    public static final String LINKER_SYMBOL = "、";

    private String scriptName;
    private String reportName;

    public ReportCollector() {
        super();
    }

    public ReportCollector(String name) {
        this();
        setName(name);
    }

    @Override
    public void testStarted() {
        testStarted("localhost");
    }

    /**
     * 测试计划（TestPlan）开始时创建 testSuite
     */
    @Override
    public void testStarted(String host){
//        scriptName = get
    }

    @Override
    public void testEnded() {
        testEnded("localhost");
    }

    /**
     * 测试计划（TestPlan）执行结束后把数据写入 HTML文件中
     */
    @Override
    public void testEnded(String host){
//        scriptName = get
    }

    /**
     * 线程(TestCase)开始时创建 testCase
     */
    @Override
    public void threadStarted(){

    }

    @Override
    public void threadFinished() {
        // pass
    }

    /**
     * 将测试样本（TestSample）的标题、请求和响应数据写入 HTML
     */
    @Override
    public void sampleOccurred(SampleEvent sampleEvent){
        SampleResult result = sampleEvent.getResult();
//        TestSuite

    }

    @Override
    public void sampleStarted(SampleEvent sampleEvent) {
        // pass
    }

    @Override
    public void sampleStopped(SampleEvent sampleEvent) {
        // pass
    }

    private void addSubResultTohtml(SampleResult parentResult, String parentId, TestCaseVo testCase) {

    }

    /**
     * 获取脚本名称（去文件后缀）
     */
    private String getScriptName() {
        String  scriptName = FileServer.getFileServer().getScriptName();
        log.debug("scriptName:[ {} ]", scriptName);
        return scriptName.substring(0, scriptName.length() - 4).trim();
    }

    @Override
    public String getThreadName() {
        return JMeterContextService.getContext().getThread().getThreadName();
    }

    /**
     * 获取当前线程的hashCode
     */
    private int getThreadHashCode() {
        return JMeterContextService.getContext().getThread().hashCode();
    }

    /**
     * 获取默认报告目录路径 ${JMETER_HOME}/htmlreport
     */
    private String getDefaultReportDirectory() {
        return JMeterUtils.getJMeterHome() + File.separator + "htmlreport";
    }

    /**
     * 获取报告目录路径
     */
    private String getReportDirectory() {
        // Non-Gui下，获取命令行的 -JreportDir 选项
        return JMeterUtils.getPropDefault(CliOptions.REPORT_DIR, getDefaultReportDirectory());
    }

    private String getReportName() {
        // Non-Gui下，命令行存在 -JreportName 选项时，优先读取 reportName
        return JMeterUtils.getPropDefault(CliOptions.REPORT_NAME, getPropertyAsString(REPORT_NAME));
    }

    private String getIsAppend() {
        // Non-Gui下，命令行存在 -JisAppend 选项时，优先读取 isAppend
        return JMeterUtils.getPropDefault(CliOptions.IS_APPEND, getPropertyAsString(IS_APPEND));
    }

    private boolean isAppend() {
        return Boolean.parseBoolean(getIsAppend());
    }

    /**
     * 获取测试报告路径
     */
    private String getReportPath() {
        return getReportDirectory() + File.separator + addHtmlSuffix(reportName);
    }

    /**
     * 为测试报告名称添加.html后缀
     */
    private String addHtmlSuffix(String name) {
        if (name.endsWith(ReportManager.HTML_SUFFIX)) {
            return name;
        } else {
            return name + ReportManager.HTML_SUFFIX;
        }
    }

    /**
     * 控制台打印执行状态信息
     */
    private void outputConsole(boolean successful, String message) {
        if (successful) {
            System.out.println("[✔][PASSED]- " + message);
        } else {
            System.err.println("[✖][FAILED]- " + message);
        }
    }

    /**
     * 获取sampler的耗时
     *
     * @param result SampleResult对象
     * @return 耗时(xx ms)
     */
    private String getSampleElapsedTime(SampleResult result) {
        return result.getEndTime() - result.getStartTime() + "ms";
    }

    @Override
    public boolean interrupt() {
        testEnded();
        return true;
    }

    private String getCurrentTime() {
        return TimeUtil.currentTimeAsString(DATA_FORMAT_PATTERN);
    }

    /**
     * 计算耗时
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    private String getElapsedTime(String startTime, String endTime) {
        return TimeUtil.formatElapsedTimeAsHMS(startTime, endTime, DATA_FORMAT_PATTERN);
    }

}
