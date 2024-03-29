package org.tester.jmeter.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.tester.jmeter.functions.core.IdCardGenerator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 随机生成身份证号
 * @author Windy.Gu
 * @date 2019-12-04 15:39
 */
@SuppressWarnings("unchecked")
public class RandomIdCardNo extends AbstractFunction {

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        return IdCardGenerator.getIdCardNo();
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
        return "__RandomIdCardNo";
    }

    public List<String> getArgumentDesc() {
        return new LinkedList();
    }
}