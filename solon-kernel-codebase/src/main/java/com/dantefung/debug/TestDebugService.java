package com.dantefung.debug;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author fenghaolin
 * @date 2024/07/25 18/14
 * @since JDK1.8
 */
public class TestDebugService {

    public List<String> checkShuttle(String message, Long companyId) {
        DebugInfoCollector.addMessage("Checking Step 1");
        DebugInfoCollector.addMessage("Checking Step 2");
        DebugInfoCollector.addMessage("Checking Step 3");
        DebugInfoCollector.setVariable("a", 1);
        DebugInfoCollector.setVariable("b", 2);
        DebugInfoCollector.setVariable("c", 3);
        return Lists.newArrayList(String.format("companyId: %s, message: %s", companyId, message));
    }

    public static void main(String[] args) {
        String message = "abc";
        Long companyId = 1L;
        TestDebugService debugService = new TestDebugService();
        ExecutionResult<List<String>> executionResult = DebugInfoTemplate.getInstance(() -> {
            return debugService.checkShuttle(message, companyId);
        }).setDebug(true).execute();
        System.out.println(executionResult.getResult());
        System.out.println(executionResult.getDebugInfo());

    }
}
