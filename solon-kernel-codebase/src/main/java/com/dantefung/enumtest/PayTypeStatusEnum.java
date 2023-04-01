package com.dantefung.enumtest;

import lombok.Getter;

/**
 * @Title: PayStatusEnum
 * @Description:
 * @author fenghaolin
 * @date 2023/04/01 13/43
 * @since JDK1.8
 */
public enum PayTypeStatusEnum implements BaseEnum {

	PRE_PAID(1, "预付"),
	ARRIVAL_PAID(2, "到付")

	;

	@Getter
	private Integer code;
	@Getter
	private String name;

	PayTypeStatusEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public Object code() {
		return code;
	}

	public static void main(String[] args) {
		String swagger = BaseEnum.swagger(PayTypeStatusEnum.class);
		System.out.println(swagger);
		System.out.println(BaseEnum.parse(PayTypeStatusEnum.class, 1));
		System.out.println(BaseEnum.parse(PayTypeStatusEnum.class, 19, PayTypeStatusEnum.PRE_PAID));
		System.out.println(BaseEnum.getItemList(PayTypeStatusEnum.class));
		System.out.println(BaseEnum.getKeyValueList(PayTypeStatusEnum.class));
	}
}
