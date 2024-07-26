package com.dantefung.debug;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 参考自: https://mp.weixin.qq.com/s/mklNPmQtskk8_Bs-rS4O6g
 * @author fenghaolin
 * @date 2024/07/25 17/49
 * @since JDK1.8
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebugInfo implements Serializable {

    private static final long serialVersionUID = 1557045781075006327L;

    /**
     * 默认名称空间
     * 单个线程内区分不同方法调用的调试信息
     */
    public static final String DEFAULT_NAMESPACE = "ns:default";

    private Map<String, Map<String, Object>> variableMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> messageMap = new ConcurrentHashMap<>();
    private Boolean debug = Boolean.FALSE;
    private boolean isDebugDetail = false;

    public Map<String, Object> getVariables() {
        return variableMap.getOrDefault(DEFAULT_NAMESPACE, new HashMap<>());
    }

    public Map<String, Object> getVariables(String namespace) {
        return variableMap.getOrDefault(namespace, new HashMap<>());
    }

    public List<String> getDefaultMessageList() {
        return messageMap.getOrDefault(DEFAULT_NAMESPACE, new ArrayList<>());
    }

    public List<String> getMessageList(String namespace) {
        return messageMap.getOrDefault(namespace, new ArrayList<>());
    }


    public void setVariable(String namespace, String name, Object value) {
        Map<String, Object> vars = variableMap.computeIfAbsent(namespace, k -> new ConcurrentHashMap<>());
        vars.put(name, value);
    }

    public void setVariable(String name, Object value) {
        setVariable(DebugInfo.DEFAULT_NAMESPACE, name, value);
    }

    public Object getVariable(String namespace, String name) {
        Map<String, Object> vars = variableMap.get(namespace);
        return vars != null ? vars.get(name) : null;
    }

    public Object getVariable(String name) {
        return getVariable(DebugInfo.DEFAULT_NAMESPACE, name);
    }

    public void debugMessage(String namespace, String message) {
        if (StringUtils.isNotBlank(message)) {
            List<String> msgs = messageMap.computeIfAbsent(namespace, k -> Collections.synchronizedList(new ArrayList<>()));
            msgs.add(message);
        }
    }

    public void debugMessage(String message) {
        debugMessage(DebugInfo.DEFAULT_NAMESPACE, message);
    }

    public void debugMessage(String namespace, String format, Object... argArray) {
        if (!debug) {
            return;
        }
        String[] msgArray = new String[argArray.length];
        for (int i = 0; i < argArray.length; i++) {
            msgArray[i] = JSON.toJSONString(argArray[i]);
        }
        FormattingTuple ft = MessageFormatter.arrayFormat(format, msgArray);
        List<String> msgs = messageMap.computeIfAbsent(namespace, k -> Collections.synchronizedList(new ArrayList<>()));
        msgs.add(ft.getMessage());
    }

    public void debugMessage(String format, Object... argArray) {
        debugMessage(DebugInfo.DEFAULT_NAMESPACE, format, argArray);
    }

    public void debugMessage(String namespace, boolean condition, String message, Supplier<?>... paramSuppliers) {
        if (!condition) {
            return;
        }
        commonDebug(namespace, message, paramSuppliers);
    }

    public void debugMessage(boolean condition, String message, Supplier<?>... paramSuppliers) {
        debugMessage(DebugInfo.DEFAULT_NAMESPACE, condition, message, paramSuppliers);
    }

    public void debugMessage(String namespace, String message, Supplier<?>... paramSuppliers) {
        if (!isDebugDetail) {
            return;
        }
        commonDebug(namespace, message, paramSuppliers);
    }

    public void debugMessage(String message, Supplier<?>... paramSuppliers) {
        debugMessage(DebugInfo.DEFAULT_NAMESPACE, message, paramSuppliers);
    }

    private void commonDebug(String namespace, String message, Supplier<?>... paramSuppliers) {
        String[] msgArray = new String[paramSuppliers.length];
        for (int i = 0; i < paramSuppliers.length; i++) {
            msgArray[i] = JSON.toJSONString(paramSuppliers[i].get());
        }
        FormattingTuple ft = MessageFormatter.arrayFormat(message, msgArray);
        List<String> msgs = messageMap.computeIfAbsent(namespace, k -> Collections.synchronizedList(new ArrayList<>()));
        msgs.add(ft.getMessage());
    }

}
