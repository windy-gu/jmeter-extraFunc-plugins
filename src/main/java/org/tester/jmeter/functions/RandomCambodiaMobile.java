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
    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        return RandomString.getCambodiaMobile();
    }
    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
        checkParameterCount(collection, 0, 0);
    }

    @Override
    public String getReferenceKey() {
        return "__RandomCambodiaMobile";
    }

    public List<String> getArgumentDesc() {
        return new LinkedList();
    }
}
