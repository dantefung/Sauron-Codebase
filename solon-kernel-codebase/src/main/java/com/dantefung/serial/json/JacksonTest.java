package com.dantefung.serial.json;

import com.dantefung.tool.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fenghaolin
 * @date 2025/01/11 17/29
 * @since JDK1.8
 */
public class JacksonTest {

    public static void main(String[] args) {
        // case 1
        ChargeDTO chargeDTO = new ChargeDTO();
        chargeDTO.setAmount0(new BigDecimal("12.4551332322"));
        // case 2
        chargeDTO.setAmount1(new BigDecimal("12.00000000"));

        chargeDTO.setAmount2(new BigDecimal("1234.5678"));

        chargeDTO.setAmount21(new BigDecimal("1234.0000"));

        chargeDTO.setAmount3(new BigDecimal("1234567.89"));

        chargeDTO.setAmount4(new BigDecimal("0.75"));

        chargeDTO.setAmount5(new BigDecimal("1234.5678"));
        System.out.println(JacksonUtils.objectToString(chargeDTO));

        ReSerialDemo reSerialDemo = new ReSerialDemo();
        reSerialDemo.setAmount(new BigDecimal("0.00"));
        reSerialDemo.setAmount1(new BigDecimal("0"));
        String json = JacksonUtils.objectToString(reSerialDemo);
        System.out.println(JacksonUtils.stringToObject(json, ReSerialDemo.class));
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReSerialDemo implements Serializable {

        private static final long serialVersionUID = 3699472848660448434L;

        @JsonFormat(pattern = "0.00")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount;

        @JsonFormat(pattern = "#.00")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount1;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChargeDTO implements Serializable {

        private static final long serialVersionUID = 2178229789494793408L;

        /**
         * 金额
         *
         * @JsonFormat(pattern = "#.##") 小数点后为0会不显示小数
         */
        @JsonFormat(pattern = "#.##")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount0;

        /**
         * 金额
         *
         * @JsonFormat(pattern = "#.##") 小数点后为0会不显示小数
         */
        @JsonFormat(pattern = "#.##")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount1;

        /**
         * 金额
         *
         */
        @JsonFormat(pattern = "#.00")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount2;

        /**
         * 金额
         *
         */
        @JsonFormat(pattern = "#.00")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount21;

        /**
         * 金额
         *
         */
        @JsonFormat(pattern = "#,###.00")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount3;

        /**
         * 金额
         *
         */
        @JsonFormat(pattern = "##.00%")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount4;

        /**
         * 金额
         *
         */
        @JsonFormat(pattern = "00000.000")
        @JsonSerialize(using = SerializerStringBigDecimal.class)
        private BigDecimal amount5;
    }

}
