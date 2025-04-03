package com.dantefung.debug;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Data
public class CommonContext {
    // 日志
    private StringBuffer logSb = new StringBuffer();
    // 日志开关
    private boolean isDebug;
    // 日志开关是否记录详细日志
    private boolean isDebugDetail;

    // 通用参数
    private boolean compare = false;
    // 中间结果
    private Set<Integer> targetSet = new HashSet<>();

    public void clearContext() {
        targetSet = Collections.emptySet();
        compare = false;
    }

    public void setDebugDetail(boolean debugDetail) {
        if (debugDetail) {
            isDebug = true;
        }
        isDebugDetail = debugDetail;
    }

    public void debug(String message) {
        if (!isDebug || StringUtils.isEmpty(message)) {
            return;
        }
        logSb.append(message).append("\t\n");
    }

    public void debug(String format, Object... argArray) {
        if (!isDebug) {
            return;
        }
        String[] msgArray = new String[argArray.length];
        for (int i = 0; i < argArray.length; i++) {
            msgArray[i] = JSON.toJSONString(argArray[i]);
        }
        FormattingTuple ft = MessageFormatter.arrayFormat(format, msgArray);
        logSb.append(ft.getMessage()).append("\t\n");
    }

    public void debug(String message, Supplier<?>... paramSuppliers) {
        if (!isDebug) {
            return;
        }
        commonDebug(message, paramSuppliers);
    }

    public void debugDetail(String message, Supplier<?>... paramSuppliers) {
        if (!isDebugDetail) {
            return;
        }
        commonDebug(message, paramSuppliers);
    }

    private void commonDebug(String message, Supplier<?>... paramSuppliers) {
        String[] msgArray = new String[paramSuppliers.length];
        for (int i = 0; i < paramSuppliers.length; i++) {
            msgArray[i] = JSON.toJSONString(paramSuppliers[i].get());
        }
        FormattingTuple ft = MessageFormatter.arrayFormat(message, msgArray);
        logSb.append(ft.getMessage()).append("\t\n");
    }

    public void debugEnd() {
        if (!isDebug) {
            return;
        }
        String msg = logSb.toString();
        log.info(msg);
    }

    public static void main(String[] args) {
        class Constants {
            public static final String SPECIAL_UUID = "abc";
            public static final String SPECIAL_UUID2 = "efg";
        }

        class Request {
            private String uuid;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }

        class ActivityInfo {
            String name;

            public ActivityInfo(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "ActivityInfo{" + "name='" + name + '\'' + '}';
            }
        }

        Request request = new Request();
        request.setUuid("efg");
        CommonContext context = new CommonContext();
        context.setDebug(Constants.SPECIAL_UUID.equals(request.getUuid()));
        context.setDebugDetail(Constants.SPECIAL_UUID2.equals(request.getUuid()));
        context.debugDetail("activityList:{}", () -> Stream.of(new ActivityInfo("张三"), new ActivityInfo("李四"))
                .map(ActivityInfo::toString)
                .collect(Collectors.joining("######")));
        context.debugEnd();
    }
}
