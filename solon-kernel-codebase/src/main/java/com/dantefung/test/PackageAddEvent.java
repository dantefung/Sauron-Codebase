package com.dantefung.test;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import com.dantefung.jmh.JacksonUtils;

import java.io.Serializable;

/**
 * @author fenghaolin
 * @date 2024/01/12 11/55
 * @since JDK1.8
 */
@Getter
@ToString(doNotUseGetters = true)
public class PackageAddEvent extends ApplicationEvent implements Serializable {


	public static final String SOURCE = "PackageAddEvent";

	/**
	 * 车行ID
	 */
	private Long companyId;

	/**
	 * 套餐ID
	 */
	private Long id;

	/**
	 * 套餐编码
	 */
	private String rateCode;

	public PackageAddEvent() {
		super(SOURCE);
	}

	public PackageAddEvent(Long id, Long companyId, String rateCode) {
		super(SOURCE);
		this.id = id;
		this.companyId = companyId;
		this.rateCode = rateCode;
	}

    public static void main(String[] args) {
        String json = "{\n" + "  \"companyId\": 96,\n" + "  \"id\": 6,\n" + "  \"rateCode\": \"test_4d48cb2e63dd\",\n"
                + "  \"timestamp\": 38,\n" + "  \"source\": {}\n" + "}";

        PackageAddEvent packageAddEvent = JacksonUtils.stringToObject(json, PackageAddEvent.class);
        System.out.println(packageAddEvent);
    }

}
