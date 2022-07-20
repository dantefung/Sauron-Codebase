/*
 * Copyright (C), 2015-2020
 * FileName: ToStringUtil
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/7/20 17:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/7/20 17:44   V1.0.0
 */
package com.dantefung.tool;

import ch.qos.logback.classic.Level;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ToStringUtil
 * @Description:
 * @author DANTE FUNG
 * @date 2022/07/20 17/44
 * @since JDK1.8
 */
@Slf4j
public class ToStringUtil {

	public static JSONArray parseArray(String requestStr) {
		JSONArray array = new JSONArray();
		try {
			array = JSON.parseArray(requestStr);
		} catch (Exception e) {
			requestStr = requestStr.trim().substring(1, requestStr.trim().length() - 1);
			String[] objs = requestStr.split("},\\s*?\\{");
			for (String obj : objs) {
				if (obj.startsWith("{")) {
					obj += "}";
				} else if (obj.endsWith("}")) {
					obj = "{" + obj;
				} else {
					obj = "{" + obj + "}";
				}
				JSONObject jsonObject = parseObject(obj);
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject parseObject(String requestStr) {
		if (log.isDebugEnabled()) {
			log.debug(">>>>>>requestStr: " + requestStr);
		}
		JSONObject obj = new JSONObject();
		try {
			obj = JSON.parseObject(requestStr);
		} catch (Exception e) {
			requestStr = requestStr.trim().substring(1, requestStr.trim().length() - 1);
			String[] keyVals = requestStr.split(",");
			if (log.isDebugEnabled()) {
				log.debug(Arrays.toString(keyVals));
			}

			for (String keyVal : keyVals) {
				if (log.isDebugEnabled()) {
					log.debug("------->" + keyVal);
				}
				String[] kv = keyVal.split("=");
				// auth_store_ids=[1163,1164]  这种就不处理了
				if (kv.length == 2) {
					String key = kv[0];
					String val = kv[1];
					obj.put(StringUtils.trim(key), (val.equals("null") || val.equals("nul")) ? "" : StringUtils.trim(val));
				}
			}
		}

		return obj;
	}


	public static void main(String[] args) throws IOException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
				.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
		//		test1();
		test2();

	}

	private static void test2() throws IOException {
		String text = "[{id=2189, username=test001, taobao_user_id=0, real_name=test001}]";
		JSONArray jsonArray = parseArray(text);
		List<String> userNameList = Lists.newArrayList();

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("D:/test.csv")));
		StringBuffer sb = new StringBuffer();
		sb.append("id").append(",").append("username").append(",").append("real_name").append(",").append("淘宝ID");
		bw.write(sb.toString());
		bw.newLine();
		for (Object o : jsonArray) {
			Map<String, String> map = (Map<String, String>) o;
			System.out.println(map);
			String id = map.get("id");
			String username = map.get("username");
			String realName = map.get("real_name");
			String taobaoUserId = map.get("taobao_user_id");
			System.out.println(username);
			userNameList.add(username);
			List<String> lineElems = Lists.newArrayList(id, username, realName, taobaoUserId);
			bw.write(StringUtils.join(lineElems, ","));
			bw.newLine();
			bw.flush();
		}
		bw.close();
		System.out.println(userNameList.size());
		System.out.println(Joiner.on(",").join(userNameList));
		List<List<String>> partition = ListUtils.partition(userNameList, 10);
		for (List<String> list : partition) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	private static void test1() {
		Map<String, String> testModel = new HashMap<>();
		testModel.put("name", "xxxx");
		testModel.put("age", "yyyy");

		Map<String, String> testModel2 = new HashMap<>();
		testModel2.put("name", "xxxx1");
		testModel2.put("age", "yyyy2");

		List<Map<String, String>> list = Lists.newArrayList(testModel, testModel2);
		// [{name=xxxx, age=yyyy}, {name=xxxx1, age=yyyy2}]
		System.out.println(list.toString());

		String text = "[{name=xxxx, age=yyyy}, {name=xxxx1, age=yyyy2}]";
		JSONArray jsonArray = parseArray(text);
		System.out.println(jsonArray.get(0));
	}
}
