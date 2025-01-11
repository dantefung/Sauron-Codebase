package com.dantefung.serial.xml.jackson;

import com.dantefung.serial.json.SerializerStringBigDecimal;
import com.dantefung.tool.XmlUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fenghaolin
 * @date 2025/01/11 17/50
 * @since JDK1.8
 */
public class JacksonXmlTest {

    public static void main(String[] args) {
        TestJacksonXmlEnvelop envelop = new TestJacksonXmlEnvelop();

        // 设置 TestJacksonXmlEnvelop 的属性
        envelop.setService("TestService");

        // 设置 Body 的属性
        TestJacksonXmlEnvelop.Body body = new TestJacksonXmlEnvelop.Body();
        envelop.setBody(body);

        // 设置 ProductRS 的属性
        TestJacksonXmlEnvelop.ProductRS productRS = new TestJacksonXmlEnvelop.ProductRS();
        body.setProductRS(productRS);

        productRS.setTimeStamp("2025-01-11T17:50:00Z");
        productRS.setTransactionIdentifier("TX123456789");
        productRS.setSequenceNmbr("1");
        productRS.setTarget("TestTarget");
        productRS.setVersion("1.0");

        // 设置 ProductCore 的属性
        TestJacksonXmlEnvelop.ProductCore productCore = new TestJacksonXmlEnvelop.ProductCore();
        productCore.setDayUnitPrice(new BigDecimal("123.451212"));
        productCore.setCapsPrice(new BigDecimal("67.8911"));
        productRS.setProductCore(productCore);

        System.out.println(envelop.toXMLString());

    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JacksonXmlRootElement(localName = "env:Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    public static class TestJacksonXmlEnvelop implements Serializable {

        private static final long serialVersionUID = 8185608410200397734L;


        @JacksonXmlProperty(isAttribute = true, localName = "xmlns:env")
        private final String xmlnsenv = "http://schemas.xmlsoap.org/soap/envelope/";

        @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsd")
        private final String xmlnsxsd = "http://www.w3.org/2001/XMLSchema";

        @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsi")
        private final String xmlnsxi = "http://www.w3.org/2001/XMLSchema-instance";

        @JacksonXmlProperty(localName = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
        private Body body;

        @JacksonXmlProperty(isAttribute = true, localName = "service")
        private String service;

        @SneakyThrows
        public String toXMLString() {
            return XmlUtil.toJacksonXml(this);
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {

            @JacksonXmlProperty(localName = "ProductRS")
            private ProductRS productRS;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProductRS {

            @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
            private final String xmlns = "http://www.opentravel.org/OTA/2003/05";

            @JacksonXmlProperty(isAttribute = true, localName = "TimeStamp")
            private String timeStamp;

            @JacksonXmlProperty(isAttribute = true, localName = "TransactionIdentifier")
            private String transactionIdentifier;

            @JacksonXmlProperty(isAttribute = true, localName = "SequenceNmbr")
            private String sequenceNmbr;

            @JacksonXmlProperty(isAttribute = true, localName = "Target")
            private String target;

            @JacksonXmlProperty(isAttribute = true, localName = "Version")
            private String version;

            @JacksonXmlProperty(localName = "ProductCore")
            private ProductCore productCore;

        }


        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProductCore implements Serializable {

            private static final long serialVersionUID = 1032668607705132538L;

            @JsonFormat(pattern = "#.00")
            @JsonSerialize(using = SerializerStringBigDecimal.class)
            @JacksonXmlProperty(isAttribute = true, localName = "DayUnitPrice")
            private BigDecimal dayUnitPrice;

            @JsonFormat(pattern = "#.00")
            @JsonSerialize(using = SerializerStringBigDecimal.class)
            @JacksonXmlProperty(isAttribute = true, localName = "CapsPrice")
            private BigDecimal capsPrice;
        }
    }


}
