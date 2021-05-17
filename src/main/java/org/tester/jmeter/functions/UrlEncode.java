package org.tester.jmeter.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.tester.jmeter.functions.core.UrlEncodeUtil;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * UrlEncode
 * @author Windy
 * @date 2021-4-02 13:15
 */
@SuppressWarnings("unchecked")
public class UrlEncode extends AbstractFunction {

    /**
     * function描述，在函数助手中函数参数中显示内容
     */
    private static final List<String> DESC = new LinkedList<String>();

    static {
        DESC.add("请输入api地址：");
        DESC.add("请输入body参数：");
    }


    private CompoundVariable api;
    private CompoundVariable body;

    /**
     * function执行的主题
     * @param sampleResult
     * @param sampler
     * @return 返回encode加密后的数据
     * @throws InvalidVariableException
     */
    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String dataEncode = "";
        String api = this.api.execute().trim();
        String body = this.body.execute().trim();
        dataEncode = UrlEncodeUtil.urlencode(api, body);
        return dataEncode;
    }

    /**
     *
     * @param parameters 入参
     * @throws InvalidVariableException
     */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        // 定义调用时，所需要的入参
        checkParameterCount(parameters, 2, 2);
        api = (CompoundVariable) parameters.toArray()[0];
        body = (CompoundVariable) parameters.toArray()[1];
    }

    /**
     * 提供jmeter函数助手显示的下来选项名称
     **/
    @Override
    public String getReferenceKey() {
        return "__UrlEncode";
    }

    /**
     * function的描述
     */
    public List<String> getArgumentDesc() {
        return DESC;
    }
}