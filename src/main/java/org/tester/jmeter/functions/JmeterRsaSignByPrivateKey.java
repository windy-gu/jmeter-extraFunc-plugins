package org.tester.jmeter.functions;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.common.json.JsonUtil;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.slf4j.Logger;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.tester.jmeter.functions.core.RsaSignByPrivateKey;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;


import java.util.*;
import java.util.Comparator;
import java.util.TreeMap;
import org.slf4j.LoggerFactory;


/**
 * UrlEncode
 * @author Windy.Gu
 * @date 2022-1-02 13:15
 */
@SuppressWarnings("unchecked")
public class JmeterRsaSignByPrivateKey extends AbstractFunction {

    /**
     * function描述，在函数助手中函数参数中显示内容
     */
    private static final Logger log = LoggerFactory.getLogger(JmeterRsaSignByPrivateKey.class);
    private static final List<String> DESC = new LinkedList<String>();

    static {
        DESC.add("请输入待加签的数据：格式(String)");
        DESC.add("请输入privateKey：");
    }


    private CompoundVariable unSignData;
    private CompoundVariable privateKey;

    /**
     * function执行的主题
     * @param sampleResult
     * @param sampler
     * @return 返回sign加密后的数据
     * @throws InvalidVariableException
     */
    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        if (!(sampler instanceof HTTPSamplerProxy)) {
            log.error("RsaSignByPrivateKey函数目前仅支持在HTTPSampler下使用");
            return "error";
        }

        String dataSign = "";
        String unSignData = this.unSignData.execute().trim();
        String privateKey = this.privateKey.execute().trim();
        log.info("待加签内容={}", unSignData);
        try {
            dataSign = RsaSignByPrivateKey.rsaSignByPrivateKey(unSignData, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSign;
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
        unSignData = (CompoundVariable) parameters.toArray()[0];
        privateKey = (CompoundVariable) parameters.toArray()[1];
    }

    /**
     * 提供jmeter函数助手显示的下来选项名称
     **/
    @Override
    public String getReferenceKey() {
        return "__RsaSignByPrivateKey";
    }

    /**
     * function的描述
     */
    public List<String> getArgumentDesc() {
        return DESC;
    }

}

