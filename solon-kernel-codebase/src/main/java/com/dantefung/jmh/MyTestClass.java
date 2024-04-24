package com.dantefung.jmh;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

// 假设的类用于测试
@Data
public class MyTestClass implements Serializable {

    private static final long serialVersionUID = -8140353824483661836L;

    @JsonProperty("goods_type")
    @JSONField(name = "goods_type")
    private String goodsType;

    @JsonProperty("quantity")
    @JSONField(name = "quantity")
    private int quantity = 1;

    @JsonProperty("days")
    @JSONField(name = "days")
    private int days = 1;

    @JsonProperty("price")
    @JSONField(name = "price")
    private BigDecimal price;

    @JsonProperty("total_price")
    @JSONField(name = "total_price")
    private BigDecimal totalPrice;

    @JsonProperty("discount_price")
    @JSONField(name = "discount_price")
    private BigDecimal discountPrice;

    @JsonProperty("discount_total_price")
    @JSONField(name = "discount_total_price")
    private BigDecimal discountTotalPrice;

    @JsonProperty("discounted_price")
    @JSONField(name = "discounted_price")
    private BigDecimal discountedPrice;

    @JsonProperty("discounted_total_price")
    @JSONField(name = "discounted_total_price")
    private BigDecimal discountedTotalPrice;

    @JsonProperty("unique_id")
    @JSONField(name = "unique_id")
    private String uniqueId;

    @JsonProperty("is_package")
    @JSONField(name = "is_package")
    private Boolean packageIncludedFlag = Boolean.FALSE;

    @JsonProperty("payment_type")
    @JSONField(name = "payment_type")
    private int paymentType = 1;

    @JsonProperty("show_scale")
    @JSONField(name = "show_scale")
    private int showScale;

    @JsonProperty("same_quantity")
    @JSONField(name = "same_quantity")
    private int sameQuantity = 0;

    @JsonProperty("calculate_quantity")
    @JSONField(name = "calculate_quantity")
    private int calculateQuantity;

    @JsonProperty("calculate_days")
    @JSONField(name = "calculate_days")
    private int calculateDays;

    @JsonProperty("per")
    @JSONField(name = "per")
    private int per = 2;

    @JsonProperty("capped_switch")
    @JSONField(name = "capped_switch")
    private int cappedSwitch = 0;

    @JsonProperty("capped_price")
    @JSONField(name = "capped_price")
    private BigDecimal cappedPrice = null;

    @JsonProperty("code")
    @JSONField(name = "code")
    private String code;

    public static MyTestClass createTestData() {
        MyTestClass myTestClass = new MyTestClass();
        myTestClass.setGoodsType("VwIvPirH");
        myTestClass.setQuantity(2134974600);
        myTestClass.setDays(1298796720);
        myTestClass.setPrice(new BigDecimal("4.771739850017122E307"));
        myTestClass.setTotalPrice(new BigDecimal("1.0402915705622716E308"));
        myTestClass.setDiscountPrice(new BigDecimal("6.360798304680507E307"));
        myTestClass.setDiscountTotalPrice(new BigDecimal("7.685815363607452E307"));
        myTestClass.setDiscountedPrice(new BigDecimal("8.878909704035921E307"));
        myTestClass.setDiscountedTotalPrice(new BigDecimal("7.353705705343269E307"));
        myTestClass.setUniqueId("AvGaeyjN");
        myTestClass.setPackageIncludedFlag(true);
        myTestClass.setPaymentType(94665405);
        myTestClass.setShowScale(1334439445);
        myTestClass.setSameQuantity(2094319888);
        myTestClass.setCalculateQuantity(2023392881);
        myTestClass.setCalculateDays(889567592);
        myTestClass.setPer(1994853115);
        myTestClass.setCappedSwitch(1639799335);
        myTestClass.setCappedPrice(new BigDecimal("7.653246952266982E307"));
        myTestClass.setCode("KeEWznaF");
        return myTestClass;
    }

}