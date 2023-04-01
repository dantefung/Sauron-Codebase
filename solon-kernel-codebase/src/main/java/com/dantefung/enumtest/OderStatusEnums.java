package com.dantefung.enumtest;

import com.google.common.base.Converter;
import com.google.common.base.Enums;
import lombok.Getter;

/**
 * @Title: OderStatusEnum
 * @Description:
 * @author fenghaolin
 * @date 2023/04/01 13/33
 * @since JDK1.8
 */
public enum OderStatusEnums implements BaseEnums<Integer, String> {

	STATUS0(0, "未审核"),
	STATUS1(1, "未审核"),
	STATUS2(2, "审核通过"),
	STATUS3(3, "审核未通过");

	@Getter
	private Integer code;
	@Getter
	private String desc;

	OderStatusEnums(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public static void main(String[] args) {
		System.out.println(BaseEnums.valueOf(OderStatusEnums.class, 1).getDesc());
		System.out.println(Enums.getField(OderStatusEnums.STATUS0));
		System.out.println(Enums.getIfPresent(OderStatusEnums.class, "生活"));
		System.out.println(Enums.stringConverter(OderStatusEnums.class));
		Converter<String, OderStatusEnums> enumConverter = Enums.stringConverter(OderStatusEnums.class);
		System.out.println(enumConverter.convert("STATUS0"));
		System.out.println(enumConverter.convert("STATUS3").getDesc());
	}
}
