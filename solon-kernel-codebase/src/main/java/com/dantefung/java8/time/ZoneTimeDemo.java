package com.dantefung.java8.time;

import com.dantefung.tool.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @date 2024/04/03 23/41
 * @since JDK1.8
 */
public class ZoneTimeDemo {

    public static void main(String[] args) {
        // 时间戳定义:  0时区的时间 距离 1970年1月1日00时00分00秒的秒数
        // 北京时间 2024-04-03 18:00:00 转时间戳 步骤如下:
        // 第一步: 北京时间 2024-04-03 18:00:00 对应 【0时区】的时间 为 2024-04-03 10:00:00
        // 第二步: 计算【0时区】的时间 为 2024-04-03 10:00:00 距离 1970年1月1日00时00分00秒的秒数
        // (所以说, 对于地球上任何一点，我们都可以获得同样的时间戳)

        // 北京时间 2024-04-03 18:00:00
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 4, 4, 18, 00, 00);
        long timestamp1 = (dateTime1.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli()) / 1000;
        // 零时区 2024-04-03 10:00:00
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 4, 4, 10, 00, 00);
        long timestamp2 = (dateTime2.atZone(ZoneId.of("Etc/GMT")).toInstant().toEpochMilli()) / 1000;
        System.out.printf("timestamp1:%d timestamp2:%d 是否相等:%s%n", timestamp1, timestamp2, timestamp2 == timestamp1);

        LocalDateTime localDateTime = DateTimeUtils.changeTimeZone(dateTime1, "Asia/Shanghai", "Etc/GMT");
        String dateStrAtZeroTimeZone = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String expectTimeStr =  dateTime2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assert StringUtils.equalsIgnoreCase(dateStrAtZeroTimeZone, expectTimeStr);


    }
}
