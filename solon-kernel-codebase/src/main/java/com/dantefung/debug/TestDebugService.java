package com.dantefung.debug;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author fenghaolin
 * @date 2024/07/25 18/14
 * @since JDK1.8
 */
public class TestDebugService {

    public static final String NAMESPACE_DO_IT = "ns:doIt";
    public static final String NAMESPACE_SHUTTLE_DEBUG = "ns:SHUTTLE_DEBUG";

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShuttleDebugInfo implements Serializable {

        private static final long serialVersionUID = 6411803554929289584L;

        /** the constant of field {@link ShuttleDebugInfo#a} */
        public static final String COL_A = "a";
        /** the constant of field {@link ShuttleDebugInfo#b} */
        public static final String COL_B = "b";
        /** the constant of field {@link ShuttleDebugInfo#c} */
        public static final String COL_C = "c";

        private Long a;
        private Long b;
        private Long c;

        public static ShuttleDebugInfo of(DebugInfo debugInfo) {

            Map<String, Object> variables = debugInfo.getVariables(NAMESPACE_SHUTTLE_DEBUG);
            variables.forEach((k, v) -> {
                String setterName = "set" + StringUtils.capitalize(k);
                ReflectUtil.getMethodByName(ShuttleDebugInfo.class, k);

            });
            ShuttleDebugInfo info = new ShuttleDebugInfo();
            info.setA(0L);
            info.setB(0L);
            info.setC(0L);
            return info;
        }

    }
    

    public List<String> checkShuttle(String message, Long companyId) {
        DebugInfoCollector.addMessage("Checking Step 1");
        DebugInfoCollector.addMessage("Checking Step 2");
        DebugInfoCollector.addMessage("Checking Step 3");
        DebugInfoCollector.addMessage("Checking Step 4: companyId-> {} message: {}", companyId, message);
        DebugInfoCollector.addMessage("1".equals("1"), "Checking Step 5 lazy compute: companyId-> {} message: {}", ()-> companyId, ()-> message);
        DebugInfoCollector.setVariable("ee", 233);
        DebugInfoCollector.setVariable("ff", 233);
        DebugInfoCollector.setVariable(ShuttleDebugInfo.COL_A, 1);
        DebugInfoCollector.setVariable(ShuttleDebugInfo.COL_B, 2);
        DebugInfoCollector.setVariable(ShuttleDebugInfo.COL_C, 3);
        doIt();
        return Lists.newArrayList(String.format("companyId: %s, message: %s", companyId, message));
    }

    private void doIt() {
        DebugInfoCollector.addNsMessage(NAMESPACE_DO_IT, "1".equals("1"), "doIt --> ***** 1");
        DebugInfoCollector.setNsVariable(NAMESPACE_DO_IT, "doItA", 1);
    }

    public static void main(String[] args) {
        String message = "abc";
        Long companyId = 1L;
        TestDebugService debugService = new TestDebugService();
        ExecutionResult<List<String>> executionResult = DebugInfoTemplate.getInstance()
                .execute(() -> debugService.checkShuttle(message, companyId));
        System.out.println(executionResult.getResult());
        DebugInfo debugInfo = executionResult.getDebugInfo();
        System.out.println(debugInfo);
        System.out.println(debugInfo.getDefaultMessageList());
        String CRLF = System.getProperty("line.separator");
        System.out.println(MessageFormat.format("{0}:" + CRLF + " {1}", NAMESPACE_DO_IT, debugInfo.getMessageList(NAMESPACE_DO_IT)));
        System.out.println(MessageFormat.format("{0}:" + CRLF + " {1}", DebugInfo.DEFAULT_NAMESPACE, debugInfo.getVariable(NAMESPACE_DO_IT, "doItA")));
        System.out.println(MessageFormat.format("{0}:" + CRLF + " {1}", NAMESPACE_DO_IT, debugInfo.getVariables(NAMESPACE_DO_IT)));
        System.out.println(MessageFormat.format("{0}:" + CRLF + " {1}", DebugInfo.DEFAULT_NAMESPACE, debugInfo.getVariables()));
        System.out.println("==================================");

        List<String> list = debugService.checkShuttle(message, companyId);
        System.out.println(list);

        System.out.println(StringUtils.capitalize("hello world"));
    }
}
