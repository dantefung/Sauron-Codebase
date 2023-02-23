/*
 * Copyright (C), 2015-2020
 * FileName: UuIdValidateUtil
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/7/5 15:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/7/5 15:20   V1.0.0
 */
package com.dantefung.tool;

import lombok.experimental.UtilityClass;
import org.jsoup.internal.StringUtil;

import java.util.UUID;

/**
 * @Title: UuIdValidateUtil
 * @Description:
 * @author DANTE FUNG
 * @date 2022/07/05 15/20
 * @since JDK1.8
 */
@UtilityClass
public class UuIdValidateUtil {

	/**
	 *
	 * uuid参数合法性校验
	 *
	 * @param uuid
	 *            全局唯一标识符（Universally Unique Identifier），共32个十六进制字符
	 */
	private void checkUUID(String uuid) {
		if (StringUtil.isBlank(uuid)) {
			throw new IllegalArgumentException("uuid can't be null or Empty !");
		}
		if (!uuid.matches("[0-9a-fA-F]{32}")) {
			throw new IllegalArgumentException("uuid was illegal!");
		}
	}

	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		checkUUID(uuid);
		String s = "/../../../../../../etc/passwd";
		checkUUID(s);
		String s1 = "..././..././..././..././..././..././..././..././etc/passwd";
		checkUUID(s1);

	}
}
