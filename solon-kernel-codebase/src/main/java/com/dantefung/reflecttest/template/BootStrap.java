/*
 * Copyright (C), 2015-2020
 * FileName: BootStrap
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/8/22 15:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/8/22 15:09   V1.0.0
 */
package com.dantefung.reflecttest.template;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: BootStrap
 * @Description:
 * @author DANTE FUNG
 * @date 2022/08/22 15/09
 * @since JDK1.8
 */
@Slf4j
public class BootStrap {

	/**
	 * 是否开启兼容模式
	 */
	private static volatile boolean openCompatibleMode = true;

	public static void main(String[] args) throws IllegalAccessException {
		OrderAddNotifyDTO orderAddNotifyDTO = new OrderAddNotifyDTO();
		orderAddNotifyDTO.setCompanyId(0L);
		orderAddNotifyDTO.setOrderId("12233445566");
		orderAddNotifyDTO.setApiOrderId("12233445566");
		orderAddNotifyDTO.setCustomerName("Mike");
		orderAddNotifyDTO.setOrderAmount("100.00");
		orderAddNotifyDTO.setCustomerPhone("13211112222");
		if (log.isDebugEnabled()) {
			log.debug(">>>>>>> kvMap: {}", JSONObject.toJSONString(orderAddNotifyDTO.getKeywordValueMap()));
		}

		String content = "（新订单提醒）\n"
				+ "订单号：[api_order_id]\n"
				+ "客户姓名：[customer_name]\n"
				+ "订单价格：[order_amount]\n"
				+ "客户电话：[customer_phone]";

		String rstContent = replaceKeywordValue(content, orderAddNotifyDTO.getKeywordValueMap());
		if (log.isDebugEnabled()) {
			log.debug(">>>>> rstContent: {}", rstContent);
		}

		String newContent = "（新订单提醒）\n"
				+ "订单号：${api_order_id}\n"
				+ "客户姓名：${customer_name}\n"
				+ "订单价格：${order_amount}\n"
				+ "客户电话：${customer_phone}";

		String newRstContent = replaceKeywordValue(newContent, orderAddNotifyDTO);
		if (log.isDebugEnabled()) {
			log.debug(">>>> newRstContent: {}", newRstContent);
		}

		String contentWithOldTpl = "（新订单提醒）\n"
				+ "订单号：[api_order_id]\n"
				+ "客户姓名：[customer_name]\n"
				+ "订单价格：[order_amount]\n"
				+ "客户电话：[customer_phone]";

		String rstContentOld = replaceKeywordValue(contentWithOldTpl, orderAddNotifyDTO);
		if (log.isDebugEnabled()) {
			log.debug(">>>>> 兼容旧模板 ~~ rstContentOld: {}", rstContentOld);
		}


		String extraNew = "{\"orderId\":\"${order_id}\",\"apiOrderId\":\"${api_order_id}\"}";
		String rstExtraNew = replaceKeywordValue(extraNew, orderAddNotifyDTO);
		if (log.isDebugEnabled()) {
			log.debug(">>>>>>> rstExtraNew: {}", rstExtraNew);
		}


		String extraOld = "{\"orderId\":\"[order_id]\",\"apiOrderId\":\"[api_order_id]\"}";
		String rstExtraOld = replaceKeywordValue(extraOld, orderAddNotifyDTO);
		if (log.isDebugEnabled()) {
			log.debug(">>>>>>>> rstExtraOld: {} ",rstExtraOld);
		}

		String urlNew = "https://www.abc.com/order.htm?orderId=${order_id}";
		String rstUrlNew = replaceKeywordValue(urlNew, orderAddNotifyDTO);
		if (log.isDebugEnabled()) {
			log.debug(">>>>>>> rstUrlNew: {}", rstUrlNew);
		}


		String urlOld = "https://www.abc.com/order.htm?orderId=[order_id]";
		String rstUrlOld = replaceKeywordValue(urlOld, orderAddNotifyDTO);
		if (log.isDebugEnabled()) {
			log.debug(">>>>>> rstUrlOld: {}", rstUrlOld);
		}


		testMatchSquareBrackets();

	}

	/**
	 * 使用关键字的值替换模板中的关键字
	 *
	 * @param content
	 * @param keywordValue
	 * @return
	 */
	public static String replaceKeywordValue(String content, AbstractKeywordValue keywordValue)
			throws IllegalAccessException {
		if (StringUtils.isBlank(content)) {
			return StringUtils.EMPTY;
		}
		String messageContent = content;
		Map<String, String> keywordValueMap = keywordValue.getKeywordValueMap();
		if (keywordValueMap != null && !keywordValueMap.isEmpty()) {

			Map<String, String> varMap = keywordValue.getVarMap();
			StrSubstitutor strSubstitutor = new StrSubstitutor(varMap);
			messageContent = strSubstitutor.replace(messageContent);

			// 兼容旧模板
			if (openCompatibleMode) {
				for (Map.Entry<String, String> kv : keywordValueMap.entrySet()) {
					String key = StringUtils.trim(kv.getKey());
					key = key.replaceAll("(\\$\\{)", "[");
					key = key.replaceAll("(\\})", "]");
					if (content.contains("[") && content.contains("]") && content.contains(key)) {
						messageContent = messageContent.replaceAll(new String("\\" + key), kv.getValue());
					}
				}
			}
		}
		return messageContent;
	}

	/**
	 * 使用关键字的值替换模板中的关键字
	 *
	 * @param content
	 * @param keywordValueMap
	 * @return
	 */
	public static String replaceKeywordValue(String content, Map<String, String> keywordValueMap) {
		if (StringUtils.isBlank(content)) {
			return StringUtils.EMPTY;
		}
		String messageContent = content;
		if (keywordValueMap != null && !keywordValueMap.isEmpty()) {
			for (Map.Entry<String, String> kv : keywordValueMap.entrySet()) {
				if (content.contains(kv.getKey())) {
					messageContent = messageContent.replaceAll(new String("\\" + kv.getKey()), kv.getValue());
				}
			}
		}
		return messageContent;
	}

	private static String doReplaceAll(String content, String messageContent, String key, String value) {
		if (content.contains(key)) {
			messageContent = messageContent.replaceAll(new String("\\" + key), value);
		}
		return messageContent;
	}

	private static void testMatchSquareBrackets() {
		String s = "[1,2,3]+[4,5,6]";
		System.out.println(s.replaceAll("(\\[)", ""));         //匹配左方括号
		System.out.println(s.replaceAll("(\\])", ""));         //匹配右方括号
		System.out.println(s.replaceAll("(\\[)|(\\])", ""));   //匹配左右方括号
		String a = "xxxxxxx${order_id}jjjjjjjjj";
		System.out.println(a.replaceAll("(\\$\\{)", "["));         //匹配${
		System.out.println(a.replaceAll("(\\})", "]"));         //匹配右方括号

		String content = "xxx[order_id]xxdfasdf";
		String regex = "\\[(\\w*)\\]";
		Pattern r = Pattern.compile(regex);
		// 现在创建 matcher 对象
		Matcher m = r.matcher(content);
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			//			System.out.println("Found value: " + m.group(1) );
			//			System.out.println("Found value: " + m.group(2) );
			//			System.out.println("Found value: " + m.group(3) );
		} else {
			System.out.println("NO MATCH");
		}

		Map map = new HashMap<>();
		map.put("ip", "127.0.0.1");
		map.put("port", "3306");
		map.put("tenantCode", "test001");

		StrSubstitutor strSubstitutor = new StrSubstitutor(map);
		String url3 = "jdbc:mysql://${ip}:${port}/${tenantCode}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
		String context3 = strSubstitutor.replace(url3);
		System.out.println(context3);

		String text3 = "${ip}";
		String text = text3.replaceAll("\\$\\{ip}", "cccc");
		System.out.println(text);

		String s1 = StringUtils.removeEnd(text3, "}");
		System.out.println(s1);
		String s2 = StrUtil.removePrefix(text3, "${");
		System.out.println(s2);


		Map<String,String> valuesMap = new HashMap();
		valuesMap.put("animal", "quick brown fox");
		valuesMap.put("target", "lazy dog");
		String templateString = "The ${animal} jumped over the ${target}.";
		StringSubstitutor sub = new StringSubstitutor(valuesMap);
		String resolvedString = sub.replace(templateString);
		System.out.println(resolvedString);
	}


}
