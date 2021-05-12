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
     * function描述
     */
    private static final List<String> DESC = new LinkedList<String>();

    static {
        DESC.add("输入需要encode内容：");
    }


    private CompoundVariable data;

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String dataEncode = "";
        String data = this.data.execute().trim();
        dataEncode = UrlEncodeUtil.urlencode(data);
        return dataEncode;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        // 定义调用时，所需要的入参
        checkParameterCount(parameters, 1, 1);
        data = (CompoundVariable) parameters.toArray()[0];
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