/*
 * Copyright (C), 2015-2020
 * FileName: Contact
 * Author:   DANTE FUNG
 * Date:     2021/7/29 14:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/29 14:32   V1.0.0
 */
package com.dantefung.yaml;

import lombok.Data;

import java.util.List;

/**
 * @Title: Contact
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/29 14/32
 * @since JDK1.8
 */
@Data
public class Contact {

	public String name;

	public int age;

	public List<Phone> phoneNumbers;
}
