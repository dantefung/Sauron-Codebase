/*
 * Copyright (C), 2015-2020
 * FileName: User
 * Author:   DANTE FUNG
 * Date:     2021/4/13 16:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/4/13 16:29   V1.0.0
 */
package com.dantefung.java8.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: User
 * @Description:
 * @author DANTE FUNG
 * @date 2021/04/13 16/29
 * @since JDK1.8
 */
@Data
@AllArgsConstructor
public class User {

	private String username;

	private String birthday;

	private int age;

}
