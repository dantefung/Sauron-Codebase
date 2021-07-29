/*
 * Copyright (C), 2015-2020
 * FileName: WebSites
 * Author:   DANTE FUNG
 * Date:     2021/7/29 15:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/29 15:52   V1.0.0
 */
package com.dantefung.yaml;

import lombok.Data;

import java.util.Map;

/**
 * @Title: WebSites
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/29 15/52
 * @since JDK1.8
 */
@Data
public class WebSites {

	private Map<String, String> websites;
}
