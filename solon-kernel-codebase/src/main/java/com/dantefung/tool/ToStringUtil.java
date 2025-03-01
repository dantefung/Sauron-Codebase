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
//				test1();
//		test2();
		test3();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 200; i++) {
			sb.append("a");
		}
		System.out.println(sb.toString());
	}

	private static void test3() {
//		String text = "[{order_id=22062810002000, COUNT(t.order_id)=5, COUNT(t.renewal_id)=5, 正常数量=0, 取消数量=5, 订单状态=已取车}, {order_id=22080186001000, COUNT(t.order_id)=4, COUNT(t.renewal_id)=4, 正常数量=2, 取消数量=2, 订单状态=已取车}, {order_id=220622300010000, COUNT(t.order_id)=13, COUNT(t.renewal_id)=13, 正常数量=1, 取消数量=12, 订单状态=已取车}, {order_id=220622304002000, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=0, 取消数量=1, 订单状态=已取车}, {order_id=220622304005000, COUNT(t.order_id)=8, COUNT(t.renewal_id)=8, 正常数量=2, 取消数量=6, 订单状态=已取车}, {order_id=220623310001000, COUNT(t.order_id)=3, COUNT(t.renewal_id)=3, 正常数量=0, 取消数量=3, 订单状态=已取车}, {order_id=220812164096292, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=220816192096534, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=220826490031534, COUNT(t.order_id)=3, COUNT(t.renewal_id)=3, 正常数量=3, 取消数量=0, 订单状态=已取车}, {order_id=220907646078343, COUNT(t.order_id)=4, COUNT(t.renewal_id)=4, 正常数量=4, 取消数量=0, 订单状态=已取车}, {order_id=220922896007080, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=220923886046289, COUNT(t.order_id)=20, COUNT(t.renewal_id)=20, 正常数量=20, 取消数量=0, 订单状态=已取车}, {order_id=220924888088639, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=220925892132383, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=220930990078134, COUNT(t.order_id)=3, COUNT(t.renewal_id)=3, 正常数量=3, 取消数量=0, 订单状态=已取车}, {order_id=220930992082623, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=221001984153961, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=221004982341602, COUNT(t.order_id)=3, COUNT(t.renewal_id)=3, 正常数量=3, 取消数量=0, 订单状态=已取车}, {order_id=221004984345554, COUNT(t.order_id)=7, COUNT(t.renewal_id)=7, 正常数量=7, 取消数量=0, 订单状态=已取车}, {order_id=221007982420044, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=221007992429554, COUNT(t.order_id)=10, COUNT(t.renewal_id)=10, 正常数量=10, 取消数量=0, 订单状态=已取车}, {order_id=221008980431000, COUNT(t.order_id)=8, COUNT(t.renewal_id)=8, 正常数量=8, 取消数量=0, 订单状态=已取车}, {order_id=221008992444076, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=221009980472751, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=221011978497933, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=221011978502182, COUNT(t.order_id)=3, COUNT(t.renewal_id)=3, 正常数量=3, 取消数量=0, 订单状态=已取车}, {order_id=2210111036013143, COUNT(t.order_id)=2, COUNT(t.renewal_id)=2, 正常数量=2, 取消数量=0, 订单状态=已取车}, {order_id=2210121028028410, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210121040024056, COUNT(t.order_id)=2, COUNT(t.renewal_id)=2, 正常数量=1, 取消数量=1, 订单状态=已取车}, {order_id=2210131040066297, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210141028073210, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210141036077926, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210141040085471, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210151026122883, COUNT(t.order_id)=2, COUNT(t.renewal_id)=2, 正常数量=2, 取消数量=0, 订单状态=已取车}, {order_id=2210151028082045, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210161036145836, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210161042174291, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2210171028151798, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=1632520131186720298, COUNT(t.order_id)=3, COUNT(t.renewal_id)=3, 正常数量=3, 取消数量=0, 订单状态=已取车}, {order_id=2715430285497203405, COUNT(t.order_id)=20, COUNT(t.renewal_id)=20, 正常数量=3, 取消数量=17, 订单状态=已取车}, {order_id=2767178990031349612, COUNT(t.order_id)=1, COUNT(t.renewal_id)=1, 正常数量=1, 取消数量=0, 订单状态=已取车}, {order_id=2768662296209931401, COUNT(t.order_id)=4, COUNT(t.renewal_id)=4, 正常数量=4, 取消数量=0, 订单状态=已取车}]";
		String text = "[{renewal_status=0, order_id=220923886046289, renewal_id=1, renewal_begin_time=2022-09-25 18:20:00.0, renewal_end_time=2022-09-26 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=2, renewal_begin_time=2022-09-26 18:30:00.0, renewal_end_time=2022-09-27 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=3, renewal_begin_time=2022-09-27 18:30:00.0, renewal_end_time=2022-09-29 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=4, renewal_begin_time=2022-09-29 18:30:00.0, renewal_end_time=2022-09-30 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=5, renewal_begin_time=2022-09-30 18:30:00.0, renewal_end_time=2022-10-01 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=6, renewal_begin_time=2022-10-01 18:30:00.0, renewal_end_time=2022-10-02 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=7, renewal_begin_time=2022-10-02 18:30:00.0, renewal_end_time=2022-10-03 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=8, renewal_begin_time=2022-10-03 18:30:00.0, renewal_end_time=2022-10-04 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=9, renewal_begin_time=2022-10-04 18:30:00.0, renewal_end_time=2022-10-05 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=10, renewal_begin_time=2022-10-05 18:30:00.0, renewal_end_time=2022-10-06 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=11, renewal_begin_time=2022-10-06 18:30:00.0, renewal_end_time=2022-10-07 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=12, renewal_begin_time=2022-10-07 18:30:00.0, renewal_end_time=2022-10-08 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=13, renewal_begin_time=2022-10-08 18:30:00.0, renewal_end_time=2022-10-09 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=14, renewal_begin_time=2022-10-09 18:30:00.0, renewal_end_time=2022-10-10 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=15, renewal_begin_time=2022-10-10 18:30:00.0, renewal_end_time=2022-10-13 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=16, renewal_begin_time=2022-10-13 18:30:00.0, renewal_end_time=2022-10-14 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=17, renewal_begin_time=2022-10-14 18:30:00.0, renewal_end_time=2022-10-15 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=18, renewal_begin_time=2022-10-15 18:30:00.0, renewal_end_time=2022-10-16 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=19, renewal_begin_time=2022-10-16 18:30:00.0, renewal_end_time=2022-10-17 18:30:00.0, deleted=0}, {renewal_status=0, order_id=220923886046289, renewal_id=20, renewal_begin_time=2022-10-17 18:30:00.0, renewal_end_time=2022-10-18 18:30:00.0, deleted=0}]";
		JSONArray jsonArray = parseArray(text);
		System.out.println(jsonArray);

	}

	private static void test2() throws IOException {
		String text = "[{id=305, username=嚯米出行, taobao_user_id=0, real_name=陈虹, super_role_id=0, seller_role_id=3, company_id=298, default_store_id=102, auth_store_ids=[102], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-06 13:58:02.0, gmt_modified=2022-07-06 13:58:02.0, version=1, extra=null, deleted=0}, {id=422, username=19521542421, taobao_user_id=0, real_name=杨康, super_role_id=0, seller_role_id=2, company_id=365, default_store_id=184, auth_store_ids=[184], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-11 10:00:02.0, gmt_modified=2022-07-11 10:00:02.0, version=1, extra=null, deleted=0}, {id=423, username=13588183763, taobao_user_id=0, real_name=车管家, super_role_id=0, seller_role_id=2, company_id=365, default_store_id=184, auth_store_ids=[184], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-11 10:00:02.0, gmt_modified=2022-07-11 10:00:02.0, version=1, extra=null, deleted=0}, {id=596, username=屋顶上的_小猫咪, taobao_user_id=0, real_name=屈苗苗, super_role_id=0, seller_role_id=2, company_id=424, default_store_id=286, auth_store_ids=[286], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-11 11:51:02.0, gmt_modified=2022-07-11 11:51:02.0, version=1, extra=null, deleted=0}, {id=721, username=likai223615, taobao_user_id=0, real_name=李凯, super_role_id=0, seller_role_id=3, company_id=501, default_store_id=349, auth_store_ids=[349], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-12 16:31:03.0, gmt_modified=2022-07-12 16:31:03.0, version=1, extra=null, deleted=0}, {id=856, username=小雨点13142007, taobao_user_id=0, real_name=王春花, super_role_id=0, seller_role_id=2, company_id=584, default_store_id=430, auth_store_ids=[430,431], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-13 14:54:02.0, gmt_modified=2022-07-13 14:54:02.0, version=1, extra=null, deleted=0}, {id=920, username=cdw13040600045, taobao_user_id=0, real_name=楚大为, super_role_id=0, seller_role_id=4, company_id=627, default_store_id=478, auth_store_ids=[478], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 09:42:02.0, gmt_modified=2022-07-14 09:42:02.0, version=1, extra=null, deleted=0}, {id=942, username=南宁闪租, taobao_user_id=0, real_name=, super_role_id=0, seller_role_id=4, company_id=643, default_store_id=488, auth_store_ids=[488], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:07:02.0, gmt_modified=2022-07-14 10:07:02.0, version=1, extra=null, deleted=0}, {id=967, username=qhy1773621346, taobao_user_id=0, real_name=秦鸿宇, super_role_id=0, seller_role_id=4, company_id=657, default_store_id=506, auth_store_ids=[506], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:22:03.0, gmt_modified=2022-07-14 10:22:03.0, version=1, extra=null, deleted=0}, {id=974, username=长春佳乐租车专营店管理, taobao_user_id=0, real_name=陈旭, super_role_id=0, seller_role_id=3, company_id=659, default_store_id=508, auth_store_ids=[508,509,510], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:24:09.0, gmt_modified=2022-07-14 10:24:09.0, version=1, extra=null, deleted=0}, {id=994, username=zzl, taobao_user_id=0, real_name=张志龙, super_role_id=0, seller_role_id=4, company_id=662, default_store_id=527, auth_store_ids=[527], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:28:05.0, gmt_modified=2022-07-14 10:28:05.0, version=1, extra=null, deleted=0}, {id=1000, username=南介 玛卡巴卡的小推车, taobao_user_id=0, real_name=李松林, super_role_id=0, seller_role_id=4, company_id=662, default_store_id=535, auth_store_ids=[535], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:28:05.0, gmt_modified=2022-07-14 10:28:05.0, version=1, extra=null, deleted=0}, {id=1009, username=朝政的tb, taobao_user_id=0, real_name=张朝政, super_role_id=0, seller_role_id=4, company_id=662, default_store_id=527, auth_store_ids=[527,530,533], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:28:05.0, gmt_modified=2022-07-14 10:28:05.0, version=1, extra=null, deleted=0}, {id=1010, username=爱购物的小公举825, taobao_user_id=0, real_name=段晓彤, super_role_id=0, seller_role_id=2, company_id=662, default_store_id=524, auth_store_ids=[524,527,530,533], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:28:05.0, gmt_modified=2022-07-14 10:28:05.0, version=1, extra=null, deleted=0}, {id=1033, username=只是歪冉冉, taobao_user_id=0, real_name=冉政, super_role_id=0, seller_role_id=4, company_id=665, default_store_id=545, auth_store_ids=[545], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 10:30:03.0, gmt_modified=2022-07-14 10:30:03.0, version=1, extra=null, deleted=0}, {id=1244, username=18999186202cl, taobao_user_id=0, real_name=陈亮, super_role_id=0, seller_role_id=2, company_id=735, default_store_id=668, auth_store_ids=[668], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 13:41:04.0, gmt_modified=2022-07-14 13:41:04.0, version=1, extra=null, deleted=0}, {id=1245, username=TB_57519739, taobao_user_id=0, real_name=陈锐, super_role_id=0, seller_role_id=2, company_id=735, default_store_id=668, auth_store_ids=[668], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 13:41:05.0, gmt_modified=2022-07-14 13:41:05.0, version=1, extra=null, deleted=0}, {id=1259, username=3974788044, taobao_user_id=0, real_name=, super_role_id=0, seller_role_id=2, company_id=739, default_store_id=670, auth_store_ids=[670], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-14 13:48:01.0, gmt_modified=2022-07-14 13:48:01.0, version=1, extra=null, deleted=0}, {id=1571, username=180063165444, taobao_user_id=0, real_name=孙瑜颖, super_role_id=0, seller_role_id=3, company_id=899, default_store_id=870, auth_store_ids=[870], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-18 11:23:22.0, gmt_modified=2022-07-18 12:42:17.0, version=2, extra=null, deleted=1}, {id=1613, username=ning15602560017, taobao_user_id=0, real_name=宁保灿, super_role_id=0, seller_role_id=4, company_id=906, default_store_id=885, auth_store_ids=[885,886,887,888,889,890,891,892,893,894,895,896,897,898,899], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-18 12:07:06.0, gmt_modified=2022-07-18 12:07:06.0, version=1, extra=null, deleted=0}, {id=1640, username=18932619919, taobao_user_id=0, real_name=刘连学, super_role_id=0, seller_role_id=2, company_id=924, default_store_id=931, auth_store_ids=[931], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-18 13:58:03.0, gmt_modified=2022-07-18 13:58:03.0, version=1, extra=null, deleted=0}, {id=1683, username=longfeng2004, taobao_user_id=0, real_name=董健, super_role_id=0, seller_role_id=2, company_id=948, default_store_id=965, auth_store_ids=[965], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-18 14:42:02.0, gmt_modified=2022-07-18 14:42:02.0, version=1, extra=null, deleted=0}, {id=1794, username=yf0991_5933621, taobao_user_id=0, real_name=杨凡, super_role_id=0, seller_role_id=3, company_id=1007, default_store_id=1039, auth_store_ids=[1039,1040,1041,1042,1043,1044,1045,1046,1047,1048], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-18 16:39:04.0, gmt_modified=2022-07-18 16:39:04.0, version=1, extra=null, deleted=0}, {id=1854, username=微微思密达620, taobao_user_id=0, real_name=魏巍, super_role_id=0, seller_role_id=3, company_id=1018, default_store_id=1102, auth_store_ids=[1102], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-18 16:49:03.0, gmt_modified=2022-07-18 16:49:03.0, version=1, extra=null, deleted=0}, {id=1947, username=sq17751082809, taobao_user_id=0, real_name=陈伯, super_role_id=0, seller_role_id=4, company_id=1053, default_store_id=1151, auth_store_ids=[1150,1153,1154,1155], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 10:54:04.0, gmt_modified=2022-07-19 10:54:04.0, version=1, extra=null, deleted=0}, {id=1956, username=dddtaobao318, taobao_user_id=0, real_name=曲涛, super_role_id=0, seller_role_id=2, company_id=1054, default_store_id=1157, auth_store_ids=[1157,1158,1159,1160], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 10:54:05.0, gmt_modified=2022-07-19 10:54:05.0, version=1, extra=null, deleted=0}, {id=1959, username=13760286620, taobao_user_id=0, real_name=李华亮, super_role_id=0, seller_role_id=3, company_id=1057, default_store_id=1161, auth_store_ids=[1161], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 10:56:04.0, gmt_modified=2022-07-19 10:56:04.0, version=1, extra=null, deleted=0}, {id=1960, username=13510665100, taobao_user_id=0, real_name=李科亮, super_role_id=0, seller_role_id=2, company_id=1057, default_store_id=1161, auth_store_ids=[1161], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 10:56:04.0, gmt_modified=2022-07-19 10:56:04.0, version=1, extra=null, deleted=0}, {id=1966, username=15984603058, taobao_user_id=0, real_name=杨梅, super_role_id=0, seller_role_id=2, company_id=1058, default_store_id=1163, auth_store_ids=[1163,1164], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 10:57:04.0, gmt_modified=2022-07-19 10:57:04.0, version=1, extra=null, deleted=0}, {id=1967, username=19114374873, taobao_user_id=0, real_name=周波, super_role_id=0, seller_role_id=2, company_id=1058, default_store_id=1163, auth_store_ids=[1163,1164], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 10:57:05.0, gmt_modified=2022-07-19 10:57:05.0, version=1, extra=null, deleted=0}, {id=1981, username=443146422@qq.com, taobao_user_id=0, real_name=王健, super_role_id=0, seller_role_id=2, company_id=1066, default_store_id=1175, auth_store_ids=[1175], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 11:31:05.0, gmt_modified=2022-07-19 11:31:05.0, version=1, extra=null, deleted=0}, {id=2033, username=小小王0420, taobao_user_id=0, real_name=王洋, super_role_id=0, seller_role_id=2, company_id=1089, default_store_id=1197, auth_store_ids=[1197], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 14:09:03.0, gmt_modified=2022-07-19 14:09:03.0, version=1, extra=null, deleted=0}, {id=2189, username=13540842291, taobao_user_id=0, real_name=罗泽磊, super_role_id=0, seller_role_id=3, company_id=1167, default_store_id=1303, auth_store_ids=[1303,1305], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-19 17:35:02.0, gmt_modified=2022-07-19 17:35:02.0, version=1, extra=null, deleted=0}, {id=2240, username=15138314206, taobao_user_id=0, real_name=张岭, super_role_id=0, seller_role_id=4, company_id=1189, default_store_id=1338, auth_store_ids=[1338], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 09:40:04.0, gmt_modified=2022-07-20 09:40:04.0, version=1, extra=null, deleted=0}, {id=2241, username=15066128879, taobao_user_id=0, real_name=吴义兰, super_role_id=0, seller_role_id=4, company_id=1189, default_store_id=1338, auth_store_ids=[1338], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 09:40:04.0, gmt_modified=2022-07-20 09:40:04.0, version=1, extra=null, deleted=0}, {id=2251, username=hua13366143456, taobao_user_id=0, real_name=潘华, super_role_id=0, seller_role_id=4, company_id=1191, default_store_id=1341, auth_store_ids=[1341], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 09:47:03.0, gmt_modified=2022-07-20 09:47:03.0, version=1, extra=null, deleted=0}, {id=2252, username=xing13944205565, taobao_user_id=0, real_name=翟星, super_role_id=0, seller_role_id=4, company_id=1191, default_store_id=1341, auth_store_ids=[1341], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 09:47:03.0, gmt_modified=2022-07-20 09:47:03.0, version=1, extra=null, deleted=0}, {id=2253, username=weidong517, taobao_user_id=0, real_name=魏云龙, super_role_id=0, seller_role_id=3, company_id=1191, default_store_id=1341, auth_store_ids=[1341], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 09:47:03.0, gmt_modified=2022-07-20 09:47:03.0, version=1, extra=null, deleted=0}, {id=2271, username=悦途聪, taobao_user_id=0, real_name=陈加聪, super_role_id=0, seller_role_id=2, company_id=1204, default_store_id=1357, auth_store_ids=[1357], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 09:57:03.0, gmt_modified=2022-07-20 09:57:03.0, version=1, extra=null, deleted=0}, {id=2302, username=wyy520520, taobao_user_id=0, real_name=魏洋洋, super_role_id=0, seller_role_id=4, company_id=1221, default_store_id=1376, auth_store_ids=[1376], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 10:09:04.0, gmt_modified=2022-07-20 10:09:04.0, version=1, extra=null, deleted=0}, {id=2341, username=山东启安汽车服务有限公司, taobao_user_id=0, real_name=赵文浩, super_role_id=0, seller_role_id=2, company_id=1241, default_store_id=1392, auth_store_ids=[1392], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 10:48:01.0, gmt_modified=2022-07-20 10:48:01.0, version=1, extra=null, deleted=0}, {id=2348, username=lcx39939, taobao_user_id=0, real_name=赵丽洁, super_role_id=0, seller_role_id=2, company_id=1243, default_store_id=1394, auth_store_ids=[1394], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 10:50:03.0, gmt_modified=2022-07-20 10:50:03.0, version=1, extra=null, deleted=0}, {id=2354, username=-494348260, taobao_user_id=0, real_name=黄秋宝, super_role_id=0, seller_role_id=2, company_id=1246, default_store_id=1397, auth_store_ids=[1397], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 10:54:03.0, gmt_modified=2022-07-20 10:54:03.0, version=1, extra=null, deleted=0}, {id=2361, username=新疆佳途租车, taobao_user_id=0, real_name=王明帅, super_role_id=0, seller_role_id=2, company_id=1249, default_store_id=1399, auth_store_ids=[1399], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:05:05.0, gmt_modified=2022-07-20 11:05:05.0, version=1, extra=null, deleted=0}, {id=2376, username=15954858585, taobao_user_id=0, real_name=朱肖明, super_role_id=0, seller_role_id=4, company_id=1259, default_store_id=1408, auth_store_ids=[1408], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:11:03.0, gmt_modified=2022-07-21 10:23:32.0, version=2, extra=null, deleted=1}, {id=2377, username=18866239575, taobao_user_id=0, real_name=杜晓明, super_role_id=0, seller_role_id=4, company_id=1259, default_store_id=1408, auth_store_ids=[1408], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:11:03.0, gmt_modified=2022-07-21 10:22:10.0, version=2, extra=null, deleted=1}, {id=2378, username=923448671@qq.com, taobao_user_id=0, real_name=朱肖明, super_role_id=0, seller_role_id=4, company_id=1259, default_store_id=1408, auth_store_ids=[1408], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:11:03.0, gmt_modified=2022-07-21 10:23:17.0, version=2, extra=null, deleted=1}, {id=2381, username=18353241055, taobao_user_id=0, real_name=李尚, super_role_id=0, seller_role_id=4, company_id=1259, default_store_id=1408, auth_store_ids=[1408], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:11:03.0, gmt_modified=2022-07-21 10:22:54.0, version=2, extra=null, deleted=1}, {id=2382, username=13370878838, taobao_user_id=0, real_name=刘永浩, super_role_id=0, seller_role_id=4, company_id=1259, default_store_id=1408, auth_store_ids=[1408], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:11:03.0, gmt_modified=2022-07-21 10:22:35.0, version=2, extra=null, deleted=1}, {id=2423, username=17603503666, taobao_user_id=0, real_name=王璐, super_role_id=0, seller_role_id=2, company_id=1270, default_store_id=1424, auth_store_ids=[1424,1425,1426,1427,1428,1429,1430,1431,1432,1433,1434,1435], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 11:25:05.0, gmt_modified=2022-07-20 11:25:05.0, version=1, extra=null, deleted=0}, {id=2484, username=13638731048, taobao_user_id=0, real_name=李谦, super_role_id=0, seller_role_id=3, company_id=1298, default_store_id=1476, auth_store_ids=[1476], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 14:33:02.0, gmt_modified=2022-07-20 14:33:02.0, version=1, extra=null, deleted=0}, {id=2485, username=18108785280, taobao_user_id=0, real_name=李谦, super_role_id=0, seller_role_id=3, company_id=1298, default_store_id=1476, auth_store_ids=[1476], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 14:33:02.0, gmt_modified=2022-07-20 14:33:02.0, version=1, extra=null, deleted=0}, {id=2486, username=15125821497, taobao_user_id=0, real_name=杨琼, super_role_id=0, seller_role_id=4, company_id=1298, default_store_id=1476, auth_store_ids=[1476], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 14:33:02.0, gmt_modified=2022-07-20 14:33:02.0, version=1, extra=null, deleted=0}, {id=2524, username=13596101011, taobao_user_id=0, real_name=, super_role_id=0, seller_role_id=2, company_id=1310, default_store_id=1488, auth_store_ids=[1488,1489,1490], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 14:55:04.0, gmt_modified=2022-07-20 14:55:04.0, version=1, extra=null, deleted=0}, {id=2534, username=18982022756, taobao_user_id=0, real_name=陈川川, super_role_id=0, seller_role_id=2, company_id=1315, default_store_id=1495, auth_store_ids=[1495,1496], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 15:05:04.0, gmt_modified=2022-07-20 15:05:04.0, version=1, extra=null, deleted=0}, {id=2564, username=我与GY初见遇你, taobao_user_id=0, real_name=买晨光, super_role_id=0, seller_role_id=2, company_id=1330, default_store_id=1511, auth_store_ids=[1511], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-20 16:10:02.0, gmt_modified=2022-07-20 16:10:02.0, version=1, extra=null, deleted=0}, {id=2631, username=l德华阿a, taobao_user_id=0, real_name=刘坤, super_role_id=0, seller_role_id=2, company_id=1365, default_store_id=1545, auth_store_ids=[1545], apply_new_store=0, status=1, read_agreement=1, gmt_create=2022-07-21 10:25:03.0, gmt_modified=2022-07-21 10:25:03.0, version=1, extra=null, deleted=0}]";
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

		String text2 = "[{id=191309, message_type=2, title=新订单通知, content=（新订单提醒）\n" + "订单号：2786467393010666635\n"
				+ "客户姓名：韩军\n" + "订单价格：4590\n" + "车型：捷达?VS5?2022?280TSI 自动进取型Pro?1.4T?SUV?自动?陕A\n"
				+ "取车时间：2022-08-03 09:00\n" + "取车方式：送车上门\n" + "客户电话：13814533581\n"
				+ ", href_url=https://rcsaas.fliggy.com/#/order/detail?orderId=2786467393010666635&appPage=orderDetail, template_content_id=278, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=843, gmt_create=2022-07-29 09:43:34.0, gmt_modified=2022-07-29 09:43:34.0, deleted=0, version=1, received_time=2022-07-29 09:43:35.0, extra={\"orderId\":\"2786467393010666635\"}, pop_status=0}, {id=191310, message_type=2, title=新订单通知, content=（新订单提醒）\n"
				+ "订单号：2786467393010666635\n" + "客户姓名：韩军\n" + "订单价格：4590\n"
				+ "车型：捷达?VS5?2022?280TSI 自动进取型Pro?1.4T?SUV?自动?陕A\n" + "取车时间：2022-08-03 09:00\n" + "取车方式：送车上门\n"
				+ "客户电话：13814533581\n"
				+ ", href_url=https://rcsaas.fliggy.com/#/order/detail?orderId=2786467393010666635&appPage=orderDetail, template_content_id=278, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=1757, gmt_create=2022-07-29 09:43:34.0, gmt_modified=2022-07-29 09:43:34.0, deleted=0, version=1, received_time=2022-07-29 09:43:35.0, extra={\"orderId\":\"2786467393010666635\"}, pop_status=0}, {id=438747, message_type=2, title=用户申请售后退款, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）用户申请售后退款，退款金额760.00元，退款原因：双方协商一致退款（其他），请您在71小时内完成处理，如超时未处理将自动退款！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175363669671663566, template_content_id=312, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=843, gmt_create=2022-08-12 23:53:02.0, gmt_modified=2022-08-12 23:53:02.0, deleted=0, version=1, received_time=2022-08-12 23:53:02.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=438748, message_type=2, title=用户申请售后退款, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）用户申请售后退款，退款金额760.00元，退款原因：双方协商一致退款（其他），请您在71小时内完成处理，如超时未处理将自动退款！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175363669671663566, template_content_id=312, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=1757, gmt_create=2022-08-12 23:53:02.0, gmt_modified=2022-08-12 23:53:02.0, deleted=0, version=1, received_time=2022-08-12 23:53:02.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=443297, message_type=2, title=退款失败, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）已拒绝退款，拒绝原因：退款金额与协商不符，退款金额元，退款原因：双方协商一致退款（其他），请您关注！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175363669671663566, template_content_id=299, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=843, gmt_create=2022-08-13 10:52:52.0, gmt_modified=2022-08-13 10:52:52.0, deleted=0, version=1, received_time=2022-08-13 10:52:52.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=443298, message_type=2, title=退款失败, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）已拒绝退款，拒绝原因：退款金额与协商不符，退款金额元，退款原因：双方协商一致退款（其他），请您关注！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175363669671663566, template_content_id=299, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=1757, gmt_create=2022-08-13 10:52:52.0, gmt_modified=2022-08-13 10:52:52.0, deleted=0, version=1, received_time=2022-08-13 10:52:52.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=485401, message_type=2, title=用户申请售后退款, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）用户申请售后退款，退款金额4590.00元，退款原因：双方协商一致退款（发票问题），请您在72小时内完成处理，如超时未处理将自动退款！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175834551836663566, template_content_id=312, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=843, gmt_create=2022-08-16 15:02:44.0, gmt_modified=2022-08-16 15:02:44.0, deleted=0, version=1, received_time=2022-08-16 15:02:45.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=485402, message_type=2, title=用户申请售后退款, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）用户申请售后退款，退款金额4590.00元，退款原因：双方协商一致退款（发票问题），请您在72小时内完成处理，如超时未处理将自动退款！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175834551836663566, template_content_id=312, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=1757, gmt_create=2022-08-16 15:02:44.0, gmt_modified=2022-08-16 15:02:44.0, deleted=0, version=1, received_time=2022-08-16 15:02:45.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=532017, message_type=2, title=退款成功, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）售后退款成功，退款说明：商家同意退款/超时未处理，退款金额4590.00元，退款原因：双方协商一致退款（发票问题），请您关注！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175834551836663566, template_content_id=304, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=843, gmt_create=2022-08-19 15:04:04.0, gmt_modified=2022-08-19 15:04:04.0, deleted=0, version=1, received_time=2022-08-19 15:04:04.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}, {id=532018, message_type=2, title=退款成功, content=您的乌鲁木齐租车订单（订单号：2786467393010666635）售后退款成功，退款说明：商家同意退款/超时未处理，退款金额4590.00元，退款原因：双方协商一致退款（发票问题），请您关注！, href_url=https://refund.m.tmall.com/dispute/wirelessDetail.htm?disputeId=175834551836663566, template_content_id=304, notice_message_id=0, show_type=1, read_status=0, company_id=574, user_id=1757, gmt_create=2022-08-19 15:04:04.0, gmt_modified=2022-08-19 15:04:04.0, deleted=0, version=1, received_time=2022-08-19 15:04:04.0, extra={\"orderId\":\"2786467393010666635\",\"apiOrderId\":\"2786467393010666635\"}, pop_status=0}]\n";
		JSONArray jsonArray2 = parseArray(text2);
		for (int i = 0; i < jsonArray2.size(); i++) {
			System.out.println(jsonArray2.get(i));

		}
	}
}
