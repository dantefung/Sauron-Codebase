package com.dantefung.reflecttest.template;

import lombok.Data;

@Data
public class OrderAddNotifyDTO extends AbstractKeywordValue {
    private static final long serialVersionUID = -4477582551630455913L;

    /**
     * 商家ID
     */
    private Long companyId;

    /**
     * 订单号
     */
    @TemplateKeyword("${order_id}")
	private String orderId;

	/**
	 * API订单号
	 */
	@TemplateKeyword("${api_order_id}")
	private String apiOrderId;

    /**
     * 客户姓名
     */
    @TemplateKeyword("${customer_name}")
    private String customerName;

    /**
     * 订单价格
     */
    @TemplateKeyword("${order_amount}")
    private String orderAmount;


    /**
     * 客户电话
     */
    @TemplateKeyword("${customer_phone}")
    private String customerPhone;

    @Override
    public Long getReceiveCompany() {
        return companyId;
    }
}