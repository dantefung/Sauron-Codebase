package com.dantefung.compare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fenghaolin
 * @date 2024/09/04 13/40
 * @since JDK1.8
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCompareDTO implements Serializable {

    private static final long serialVersionUID = 8842117325531500028L;
    private Long id;

    private Long companyId;

    private Long storeId;

    private Long vehGroupId;

    private Integer saleFlag;

    private Integer noDeposit;

    private BigDecimal deposit;

    private BigDecimal violationDeposit;

    private Integer mileageLimit;

    private Integer limitFlag;

    private BigDecimal extraMileagePrice;

    private BigDecimal pricePerDay;

    private Integer usePerDayFlag;

    private Long addTime;

    private Integer listOrder;

    private BigDecimal priceWeekend;

    private BigDecimal priceHour;

    private Integer zhimaFlag;

    private Integer serviceType;

}

