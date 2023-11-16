package com.dantefung.tool;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.chinese.LunarFestival;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://apidoc.gitee.com/dromara/hutool/cn/hutool/core/date/package-summary.html
 */
public class HolidayChecker {

    public static void main(String[] args) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);


		List<Integer> months = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		List<String> festervalCodes = Lists.newArrayList(HolidayConstant.NEW_YEAR_DAY,
				HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL,
				HolidayConstant.QINGMING_FESTIVAL,
				HolidayConstant.LABOR_DAY,
				HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL,
				HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL,
				HolidayConstant.NATIONAL_DAY);


		List<Pair<String, String>> festervalPairs = Lists.newArrayList();
		Map<String, TemplateDTO> templateMap = Maps.newHashMap();
		for (Integer m : months) {

			LocalDateTime start = LocalDateTime.of(year, m, 1, 0, 0, 0, 0);
			LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth());

			Console.log("=================={}-{} START ===================", year, m);
			for (String festervalCode : festervalCodes) {
				Date holiday = getHoliday(festervalCode, year);
				LocalDateTime holidayDateTime = DateTimeUtils.dateToLocalDateTime(holiday);
				TemplateDTO holidayTemplateDTO = getHolidayTemplate(festervalCode, year);
				String holidayDateStr = DateTimeUtils.dateToStr(holiday, DateTimeUtils.LOCALDATE_FORMAT_PATTERN);
				Console.log("当前月份:{} 节日:{} {} 是否当月节日:{} 默认模板: {}",
						m,
						festervalCode, holidayDateStr,
						DateTimeUtils.isBetweenTimes(holidayDateTime, start, end),
						DateTimeUtils.isBetweenTimes(holidayDateTime, start, end) ? JSONObject.toJSONString(holidayTemplateDTO) : "无"
				);

				if (DateTimeUtils.isBetweenTimes(holidayDateTime, start, end)) {
					festervalPairs.add(new Pair(holidayDateStr, festervalCode));
					templateMap.put(holidayDateStr, holidayTemplateDTO);
				}
			}
			Console.log("=================={}-{} END ===================", year, m);
		}

		Map<String,String> verifyMap = loadVerifyMap();
		for (Pair<String, String> festervalPair : festervalPairs) {
			String waitVerifyDateStr = festervalPair.getKey();
			String waitVerifyHolidayName = festervalPair.getValue();
			Console.log("待验证: {} {} 预期: {} {} 生成对应默认模板: {}",
					waitVerifyHolidayName,
					waitVerifyDateStr,
					verifyMap.containsKey(waitVerifyDateStr),
					verifyMap.get(waitVerifyDateStr),
					JSONObject.toJSONString(templateMap.get(waitVerifyDateStr))
			);
		}

		//test3(year);

		//test2();

		//test1();




//        // 判断当前日期是否是中秋节
//        boolean isMidAutumnFestival = chineseDate.isFesterval(LunarFestival.MID_AUTUMN);
//        System.out.println("当前日期是否是中秋节：" + isMidAutumnFestival);
//
//        // 判断当前日期是否是劳动节
//        boolean isLaborDay = chineseDate.isFesterval(LunarFestival.LABOR_DAY);
//        System.out.println("当前日期是否是劳动节：" + isLaborDay);
//
//        // 判断当前日期是否是端午节
//        boolean isDragonBoatFestival = chineseDate.isFesterval(LunarFestival.DRAGON_BOAT);
//        System.out.println("当前日期是否是端午节：" + isDragonBoatFestival);
//
//        // 判断当前日期是否是春节
//        boolean isSpringFestival = chineseDate.isFesterval(LunarFestival.SPRING);
//        System.out.println("当前日期是否是春节：" + isSpringFestival);
//
//        // 判断当前日期是否是清明节
//        boolean isQingMingFestival = chineseDate.isFesterval(LunarFestival.CLEAR_AND_BRIGHT);
//        System.out.println("当前日期是否是清明节：" + isQingMingFestival);
    }

	private static Map<String, String> loadVerifyMap() {
		String verfiyData = loadVerfiyData();
		JSONObject jsonObject = JSONObject.parseObject(verfiyData);
		JSONObject holidayJsonObj = jsonObject.getJSONObject("holiday");
		Map<String, String> map = holidayJsonObj.entrySet().stream().map(Map.Entry::getValue)
				.map(x -> (JSONObject) x).collect(Collectors.toMap(x -> x.getString("date"), x -> x.getString("name")));
		//Console.log(map);
		return map;
	}

	private static void test3(int year) {
		// 验证: https://timor.tech/api/holiday/year
		// 新历节日
		// 元旦
		Console.log("{} 时间为: {}", HolidayConstant.NEW_YEAR_DAY, getHoliday(HolidayConstant.NEW_YEAR_DAY, year));
		// 清明节
		Date qingmingFestival = getHoliday(HolidayConstant.QINGMING_FESTIVAL, year);
		System.out.println(HolidayConstant.QINGMING_FESTIVAL +"时间为：" + qingmingFestival);
		// 劳动节
		Console.log("{} 时间为: {}", HolidayConstant.LABOR_DAY, getHoliday(HolidayConstant.LABOR_DAY, year));
		// 国庆节
		Console.log("{} 时间为: {}", HolidayConstant.NATIONAL_DAY, getHoliday(HolidayConstant.NATIONAL_DAY, year));


		// 农历节日
		// 春节
		Console.log("{} 时间为: {} 农历: {}", HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL, getHoliday(HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL,
				year), new ChineseDate(year, 1, 1).toStringNormal());
		// 端午节
		Console.log("{} 时间为: {}", HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL, getHoliday(HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL,
				year));
		// 中秋节
		Console.log("{} 时间为: {}", HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL, getHoliday(HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL,
				year));

		Console.log("{}", LunarFestival.getFestivals(2023, 1, 1));
		ChineseDate cdate = new ChineseDate(2024, 1, 1);
		// 农历年的1.1号  跟新历年 年份一致
		Console.log("{} {} {}", LunarFestival.getFestivals(2024, 1, 1), cdate.getGregorianDate(), cdate.toStringNormal());
	}

	public static void test2() {

		// 清明节
		String qingmingStartDate = "2023-04-03";
		String qingmingEndDate = "2023-04-05";

		String qingmingStartLunarDate = DateUtil.formatChineseDate(DateUtil.parse(qingmingStartDate), false, true);
		String qingmingEndLunarDate = DateUtil.formatChineseDate(DateUtil.parse(qingmingEndDate),false, true);
		System.out.println("清明节：" + qingmingStartLunarDate + " - " + qingmingEndLunarDate);

		// 劳动节
		String laodongStartDate = "2023-04-30";
		String laodongEndDate = "2023-05-05";

		String laodongStartLunarDate = DateUtil.formatChineseDate(DateUtil.parse(laodongStartDate), false, true);
		String laodongEndLunarDate = DateUtil.formatChineseDate(DateUtil.parse(laodongEndDate), false, true);
		System.out.println("劳动节：" + laodongStartLunarDate + " - " + laodongEndLunarDate);
	}

	public static void test1() {
		ChineseDate chineseNewYearLunarStart = new ChineseDate(2023, 1, 1);
		Date chineseNewYearStart = chineseNewYearLunarStart.getGregorianDate();
		Date date = DateTimeUtils.addDays(chineseNewYearStart, -1);
		System.out.println(DateTimeUtils.dateToStr(date));
		System.out.println(DateTimeUtils.dateToStr(chineseNewYearStart));

	}

	public static class HolidayConstant {

		/**
		 * 元旦节
		 */
		public final static String NEW_YEAR_DAY = "元旦节";
		/**
		 * 春节(公历日期的)
		 */
		public final static String CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL = "春节";
		/**
		 * 清明节
		 */
		public final static String QINGMING_FESTIVAL = "清明节";
		/**
		 * 劳动节
		 */
		public final static String LABOR_DAY = "劳动节";
		/**
		 * 端午节(公历日期的)
		 */
		public final static String DRAGON_BOAT_GREGORIAN_FESTIVAL = "端午节";
		/**
		 * 中秋节(公历日期的)
		 */
		public final static String MID_AUTUMN_GREGORIAN_FESTIVAL = "中秋节";
		/**
		 * 国庆节
		 */
		public final static String NATIONAL_DAY = "国庆节";
	}

	/**
	 * 获取假期时间
	 * @param holidayName 假期名称
	 * @param year 当前年份
	 * @return
	 */
	public static Date getHoliday(String holidayName, int year) {
		switch (holidayName) {
			case HolidayConstant.NEW_YEAR_DAY:

				return DateUtil.parse(year + "-1-1");

			case HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL:

				ChineseDate chinesenewYearFestival = new ChineseDate(year, 1, 1);
				return chinesenewYearFestival.getGregorianDate();

			case HolidayConstant.QINGMING_FESTIVAL:

				int param = year - 2000;
				int qingmingDay = (int) (param * 0.2422 + 4.81) - param / 4;
				return DateUtil.parse(year + "-4-" + qingmingDay);

			case HolidayConstant.LABOR_DAY:

				return DateUtil.parse(year + "-5-1");

			case HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL:

				ChineseDate dragonBoatFestival = new ChineseDate(year, 5, 5);
				return dragonBoatFestival.getGregorianDate();

			case HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL:

				ChineseDate midAutumnFestival = new ChineseDate(year, 8, 15);
				return midAutumnFestival.getGregorianDate();

			case HolidayConstant.NATIONAL_DAY:

				return DateUtil.parse(year + "-10-1");

			default:
				return new Date();
		}
	}

	/**
	 * 获取默认放假模板
	 *
	 * 元且: 新历12.30-1.1
	 * 春节:农历年29-正月初六
	 * 清明节:新历4.3-4.5
	 * 劳动节:新历4.30-5.5
	 * 端午节: 农历5月初四到5月初六
	 * 中秋节:农历9月十四到9月十六
	 * 国庆节: 新历9.30-10.7
	 * @param holidayName 节假日名
	 * @param year 当前年份
	 * @return
	 */
	public static TemplateDTO getHolidayTemplate(String holidayName, int year) {
		switch (holidayName) {
			case HolidayConstant.NEW_YEAR_DAY:

				return new TemplateDTO(HolidayConstant.NEW_YEAR_DAY, DateUtil.parse(year + "-12-30"), DateUtil.parse(year + "-1-1"));

			case HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL:

				// 农历日
				ChineseDate chineseNewYearLunarStart = new ChineseDate(year, 1, 1);
				ChineseDate chineseNewYearLunarEnd = new ChineseDate(year, 1, 6);

				// 转公历日
				// 农历新年的前一天是除夕夜
				Date chineseNewYearStart = chineseNewYearLunarStart.getGregorianDate();
				Date newYearHolidayEve = DateTimeUtils.addDays(chineseNewYearStart, -1);
				Date newYearHolidayEnd = chineseNewYearLunarEnd.getGregorianDate();

				return new TemplateDTO(HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL, newYearHolidayEve, newYearHolidayEnd);

			case HolidayConstant.QINGMING_FESTIVAL:

				// 公历日
				Date qingmingGregorianDateStart = DateUtil.parse(year + "-4-3");
				Date qingmingGregorianDateEnd = DateUtil.parse(year + "-4-5");

				return new TemplateDTO(HolidayConstant.QINGMING_FESTIVAL, qingmingGregorianDateStart, qingmingGregorianDateEnd);

			case HolidayConstant.LABOR_DAY:

				// 公历日
				Date laborGregorianDateStart = DateUtil.parse(year + "-4-30");
				Date laborGregorianDateEnd = DateUtil.parse(year + "-5-5");
				return new TemplateDTO(HolidayConstant.LABOR_DAY, laborGregorianDateStart, laborGregorianDateEnd);

			case HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL:

				// 端午节默认模板: 农历5月初四到5月初六
				// 农历日
				ChineseDate dragonBoatLunarStart = new ChineseDate(year, 5, 4);
				ChineseDate dragonBoatLunarEnd = new ChineseDate(year, 5, 6);

				// 转公历日
				Date dragonGregorianDateStart = dragonBoatLunarStart.getGregorianDate();
				Date dragonGregorianDateEnd = dragonBoatLunarEnd.getGregorianDate();

				return new TemplateDTO(HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL, dragonGregorianDateStart, dragonGregorianDateEnd);

			case HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL:

				// 中秋节默认模板:农历9月十四到9月十六
				// 农历日
				ChineseDate midAutumnLunarStart = new ChineseDate(year, 9, 14);
				ChineseDate midAutumnLunarEnd = new ChineseDate(year, 9, 16);

				// 转公历日
				Date midAutumnGregorianDateStart = midAutumnLunarStart.getGregorianDate();
				Date midAutumnGregorianDateEnd = midAutumnLunarEnd.getGregorianDate();

				return new TemplateDTO(HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL, midAutumnGregorianDateStart, midAutumnGregorianDateEnd);

			case HolidayConstant.NATIONAL_DAY:

				// 国庆节默认模板: 新历9.30-10.7
				// 公历日
				Date nationalGregorianDateStart = DateUtil.parse(year + "-9-30");
				Date nationalGregorianDateEnd = DateUtil.parse(year + "-10-7");

				return new TemplateDTO(HolidayConstant.NATIONAL_DAY, nationalGregorianDateStart, nationalGregorianDateEnd);

			default:
				return null;
		}
	}

	@Data
	@Accessors(chain = true)
	@AllArgsConstructor
	public static class TemplateDTO implements Serializable {

		private static final long serialVersionUID = -8065191366356583041L;

		private String name;

		@JSONField(format="yyyy-MM-dd")
		private Date start;

		@JSONField(format="yyyy-MM-dd")
		private Date end;
	}


	/**
	 * 数据来自 https://timor.tech/api/holiday/year
	 * @return
	 */
	public static String loadVerfiyData() {
		String json = "{\"code\":0,\"holiday\":{\"01-01\":{\"holiday\":true,\"name\":\"元旦\",\"wage\":3,\"date\":\"2023-01-01\"},\"01-02\":{\"holiday\":true,\"name\":\"元旦\",\"wage\":2,\"date\":\"2023-01-02\"},\"01-21\":{\"holiday\":true,\"name\":\"除夕\",\"wage\":3,\"date\":\"2023-01-21\"},\"01-22\":{\"holiday\":true,\"name\":\"初一\",\"wage\":3,\"date\":\"2023-01-22\"},\"01-23\":{\"holiday\":true,\"name\":\"初二\",\"wage\":3,\"date\":\"2023-01-23\"},\"01-24\":{\"holiday\":true,\"name\":\"初三\",\"wage\":3,\"date\":\"2023-01-24\"},\"01-25\":{\"holiday\":true,\"name\":\"初四\",\"wage\":2,\"date\":\"2023-01-25\"},\"01-26\":{\"holiday\":true,\"name\":\"初五\",\"wage\":2,\"date\":\"2023-01-26\"},\"01-27\":{\"holiday\":true,\"name\":\"初六\",\"wage\":2,\"date\":\"2023-01-27\"},\"01-28\":{\"holiday\":false,\"name\":\"春节后补班\",\"wage\":1,\"after\":true,\"target\":\"春节\",\"date\":\"2023-01-28\"},\"01-29\":{\"holiday\":false,\"name\":\"春节后补班\",\"wage\":1,\"after\":true,\"target\":\"春节\",\"date\":\"2023-01-29\"},\"04-05\":{\"holiday\":true,\"name\":\"清明节\",\"wage\":3,\"date\":\"2023-04-05\",\"rest\":49},\"04-23\":{\"holiday\":false,\"name\":\"劳动节前补班\",\"wage\":1,\"target\":\"劳动节\",\"after\":false,\"date\":\"2023-04-23\"},\"04-29\":{\"holiday\":true,\"name\":\"劳动节\",\"wage\":2,\"date\":\"2023-04-29\"},\"04-30\":{\"holiday\":true,\"name\":\"劳动节\",\"wage\":2,\"date\":\"2023-04-30\"},\"05-01\":{\"holiday\":true,\"name\":\"劳动节\",\"wage\":3,\"date\":\"2023-05-01\"},\"05-02\":{\"holiday\":true,\"name\":\"劳动节\",\"wage\":3,\"date\":\"2023-05-02\"},\"05-03\":{\"holiday\":true,\"name\":\"劳动节\",\"wage\":3,\"date\":\"2023-05-03\"},\"05-06\":{\"holiday\":false,\"name\":\"劳动节后补班\",\"after\":true,\"wage\":1,\"target\":\"劳动节\",\"date\":\"2023-05-06\"},\"06-22\":{\"holiday\":true,\"name\":\"端午节\",\"wage\":3,\"date\":\"2023-06-22\"},\"06-23\":{\"holiday\":true,\"name\":\"端午节\",\"wage\":3,\"date\":\"2023-06-23\"},\"06-24\":{\"holiday\":true,\"name\":\"端午节\",\"wage\":2,\"date\":\"2023-06-24\"},\"06-25\":{\"holiday\":false,\"name\":\"端午节后补班\",\"wage\":1,\"target\":\"端午节\",\"after\":true,\"date\":\"2023-06-25\"},\"09-29\":{\"holiday\":true,\"name\":\"中秋节\",\"wage\":3,\"date\":\"2023-09-29\"},\"09-30\":{\"holiday\":true,\"name\":\"中秋节\",\"wage\":3,\"date\":\"2023-09-30\"},\"10-01\":{\"holiday\":true,\"name\":\"国庆节\",\"wage\":3,\"date\":\"2023-10-01\"},\"10-02\":{\"holiday\":true,\"name\":\"国庆节\",\"wage\":3,\"date\":\"2023-10-02\"},\"10-03\":{\"holiday\":true,\"name\":\"国庆节\",\"wage\":2,\"date\":\"2023-10-03\"},\"10-04\":{\"holiday\":true,\"name\":\"国庆节\",\"wage\":2,\"date\":\"2023-10-04\"},\"10-05\":{\"holiday\":true,\"name\":\"国庆节\",\"wage\":2,\"date\":\"2023-10-05\"},\"10-06\":{\"holiday\":true,\"name\":\"国庆节\",\"wage\":2,\"date\":\"2023-10-06\"},\"10-07\":{\"holiday\":false,\"after\":true,\"wage\":1,\"name\":\"国庆节后补班\",\"target\":\"国庆节\",\"date\":\"2023-10-07\"},\"10-08\":{\"holiday\":false,\"after\":true,\"wage\":1,\"name\":\"国庆节后补班\",\"target\":\"国庆节\",\"date\":\"2023-10-08\"},\"12-30\":{\"holiday\":true,\"name\":\"元旦\",\"wage\":2,\"date\":\"2023-12-30\",\"rest\":44},\"12-31\":{\"holiday\":true,\"name\":\"元旦\",\"wage\":2,\"date\":\"2023-12-31\"}}}\n";
		return json;
	}
}
