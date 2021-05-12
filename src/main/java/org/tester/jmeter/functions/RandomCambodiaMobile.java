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

@SuppressWarnings("unchecked")
public class RandomCambodiaMobile extends AbstractFunction{
    /**
     * function的执行主体
     * @param sampleResult
     * @param sampler
     * @return
     * @throws InvalidVariableException
     */
    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        return RandomString.getCambodiaMobile();
    }

    /**
     * 设置function的入参
     * @param collection
     * @throws InvalidVariableException
     */
    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
        // 检查参数个数
        checkParameterCount(collection, 0, 0);
    }

    /**
     * function名称
     */
    private static final String KEY = "__RandomCambodiaMobile";


    /**
     * function描述
     */
    private static final List<String> DESC = new LinkedList<String>();

    static {
        DESC.add("随机输出柬埔寨号码");
    }

    /**
     * 提供jmeter函数助手显示的选项名称 & 引用关键字
     **/
    @Override
    public String getReferenceKey() {
        return KEY;
    }


    /**
     * function的描述
     */
    public List<String> getArgumentDesc() {
        return DESC;
    }
}
