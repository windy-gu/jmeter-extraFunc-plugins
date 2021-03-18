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
 * 随机生成电子邮箱
 * @author smooth
 * @date 2019-12-02 13:15
 */
@SuppressWarnings("unchecked")
public class RandomEmail extends AbstractFunction {

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        return RandomString.getEmail();
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
        return "__RandomEmail";
    }

    public List<String> getArgumentDesc() {
        return new LinkedList();
    }
}