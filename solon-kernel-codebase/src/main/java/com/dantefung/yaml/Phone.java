/*
 * Copyright (C), 2015-2020
 * FileName: Phone
 * Author:   DANTE FUNG
 * Date:     2021/7/29 14:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/29 14:41   V1.0.0
 */
package com.dantefung.yaml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: Phone
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/29 14/41
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

	private String name;

	private String number;

}
