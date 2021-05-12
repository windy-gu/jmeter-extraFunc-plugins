package org.tester.jmeter.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.tester.jmeter.functions.core.RandomString;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 随机生成手机号
 * @author Windy
 * @date 2021-4-02 13:15
 */
@SuppressWarnings("unchecked")
public class RandomMobile extends AbstractFunction {

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        return RandomString.getMobile();
    }

    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
        checkParameterCount(collection, 0, 0);
    }

    /**
     * 提供jmeter函数助手显示的下来选项名称
     **/
    @Override
    public String getReferenceKey() {
        return "__RandomMobile";
    }

    public List<String> getArgumentDesc() {
        return new LinkedList();
    }
}