package com.dantefung.tool;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.chinese.LunarFestival;
import cn.hutool.core.lang.Console;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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

        // 创建ChineseDate对象
        ChineseDate chineseDate = new ChineseDate(year, month, day);

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
		Console.log("{} 时间为: {} 农历: {}", HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL, getHoliday(HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL, year), new ChineseDate(year, 1, 1).toStringNormal());
		// 端午节
		Console.log("{} 时间为: {}", HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL, getHoliday(HolidayConstant.DRAGON_BOAT_GREGORIAN_FESTIVAL, year));
		// 中秋节
		Console.log("{} 时间为: {}", HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL, getHoliday(HolidayConstant.MID_AUTUMN_GREGORIAN_FESTIVAL, year));

		Console.log("{}", LunarFestival.getFestivals(2023, 1, 1));
		ChineseDate cdate = new ChineseDate(2024, 1, 1);
		// 农历年的1.1号  跟新历年 年份一致
		Console.log("{} {} {}", LunarFestival.getFestivals(2024, 1, 1), cdate.getGregorianDate(), cdate.toStringNormal());

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
	/*public static TemplateDTO getHolidayTemplate(String holidayName, int year) {
		switch (holidayName) {
			case HolidayConstant.NEW_YEAR_DAY:

				return new TemplateDTO(HolidayConstant.NEW_YEAR_DAY, DateUtil.parse(year + "-12-30"), DateUtil.parse(year + "-1-1"));

			case HolidayConstant.CHINESE_NEW_YEAR_GREGORIAN_FESTIVAL:

				ChineseDate chineseNewYearLunarStart = new ChineseDate(year, 1, 1);
				Date chineseNewYearStart = chineseNewYearLunarStart.getGregorianDate();
				// TODO fhl: 减少一天

				ChineseDate chineseNewYearLunarEnd = new ChineseDate(year, 1, 6);
				Date chineseNewYearEnd = chineseNewYearLunarStart.getGregorianDate();

				return ;

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
				return null;
		}
	}*/

	@Data
	@Accessors(chain = true)
	@AllArgsConstructor
	public static class TemplateDTO implements Serializable {

		private static final long serialVersionUID = -8065191366356583041L;

		private String name;

		private Date start;

		private Date end;
	}

}
