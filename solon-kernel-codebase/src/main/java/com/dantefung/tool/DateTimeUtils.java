package com.dantefung.tool;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DateTimeUtils {

    public static final String LOCALDATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String LOCALDATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static final String TIME_MINUTE_PATTERN = "HH:mm";

    public static final DateTimeFormatter LOCALDATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter LOCALDATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter LOCALDATETIME_FORMAT_YMDHS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter yyyyMMddHHmmssSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");

    public static final DateTimeFormatter INT_LOCALDATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter LOCALTIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final DateTimeFormatter LOCALTIME_FORMAT_MIN = DateTimeFormatter.ofPattern("HH:mm");

    public static final DateTimeFormatter LOCALTIME_SINGLE_FORMAT_MIN = DateTimeFormatter.ofPattern("H:m");

    public static final DateTimeFormatter SLASH_LOCALDATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static final DateTimeFormatter SLASH_LOCALDATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static final DateTimeFormatter SLASH_SINGLE_LOCALDATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");

    public static final DateTimeFormatter LOCALDATETIME_FORMAT1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static final String ZERO_TIME_ZONE = "Etc/GMT";

    /**
     * 时秒转换单位，一小时=3600秒
     */
    public static final int SECONDS_IN_ONE_HOUR = 3600;

    /**
     * 半小时的秒数
     */
    public static final int SECONDS_IN_HALF_HOUR = 1800;

    /**
     * 东八时区偏移量
     */
    public static final int OFFSET_HOURS = 8;


    private DateTimeUtils() {
    }

    /**
     * date转换为epochSecond
     *
     * @param localDateTime
     * @return
     */
    public static Long epochSecondValueOf(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /**
     * date转换为epochSecond
     *
     * @param date
     * @return
     */
    public static Long epochSecondValueOf(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /**
     * date转换为localDate
     *
     * @param date
     * @return
     */
    public static LocalDate localDateValueOf(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 时间戳转换为localDate
     *
     * @param timestamp
     * @return
     */
    public static LocalDate localDateValueOf(Long timestamp) {
        return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * date转换为localDate
     *
     * @param date
     * @return
     */
    public static LocalDateTime localDateTimeValueOf(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 将以天为单位或以秒为单位的时间字符串转为时间戳
     *
     * @param dateStr
     * @param offsetHours
     * @return
     */
    /*public static int strToTimeStamp(String dateStr, Integer offsetHours) {
        int strCount = Splitter.on(SymbolConstants.COLON).splitToList(dateStr).size();
        int result;
        switch (strCount) {
            case 1:
                result = dateStrToTimeStamp(dateStr, offsetHours);
                break;
            case 2:
                result = dateTimeHourStrToTimeStamp(dateStr, offsetHours);
                break;
            default:
                result = dateTimeStrToTimeStamp(dateStr, offsetHours);
                break;
        }
        return result;
    }*/

    /**
     * 根据字符串 YYYY-MM-dd hh:mm:ss 转时间戳
     *
     * @param dateStr
     * @param offsetHours
     * @return
     */
    public static int dateTimeStrToTimeStamp(String dateStr, Integer offsetHours) {
        LocalDateTime localDateTime = strToLocalDateTime(dateStr, LOCALDATETIME_FORMAT);
        return dateTimeToTimeStamp(localDateTime, offsetHours);
    }

    /**
     * 转指定时区的时间戳
     *
     * @param dateStr 日期字符串
     * @param timeZone 指定时区字符串，例："America/New_York"
     * @param formatter DateTimeUtils.LOCALDATETIME_FORMAT
     * @return
     */
    public static long dateTimeStrToTimezoneTimeStamp(String dateStr, String timeZone, DateTimeFormatter formatter) {
        return (LocalDateTime.parse(dateStr, formatter).atZone(ZoneId.of(timeZone)).toInstant().toEpochMilli()) / 1000;
    }

	/**
	 * 转指定时区的时间戳
	 * @param dateStr 日期字符串 yyyy-MM-dd
	 * @param timeZone 指定时区字符串，例："America/New_York"
	 * @return
	 */
	public static long dateStrConvert2TimeStamp(String dateStr, String timeZone) {
    	return dateTimeStrToTimezoneTimeStamp(StringUtils.join(dateStr, " 00:00:00"), timeZone, DateTimeUtils.LOCALDATETIME_FORMAT);
	}

	/**
	 * 转指定时区的时间戳
	 * @param dateStr 日期字符串 yyyy-MM-dd
	 * @param timeZone 指定时区字符串，例："America/New_York"
	 * @return
	 */
	public static long dateStrConvert2TimeStamp(String dateStr, String timeZone, DateTimeFormatter formatter) {
		return dateTimeStrToTimezoneTimeStamp(dateStr, timeZone, formatter);
	}

    /**
     * 转指定时区的时间戳
     *
     * @param localDateTime
     * @param timeZone 指定时区字符串，例："America/New_York"
     * @return
     */
    public static long localDateTimeToTimezoneTimeStamp(LocalDateTime localDateTime, String timeZone) {
        return (localDateTime.atZone(ZoneId.of(timeZone)).toInstant().toEpochMilli()) / 1000;
    }

    public static long getTimeLongSecsByTimeSecs(int timeStamp) {
        return timeStamp * 1000L;
    }

    /**
     * 把字符串转化成LocalDate
     *
     * @param dateStr
     * @return
     */
    public static LocalDate strToLocalDate(String dateStr) {
        return strToLocalDate(dateStr, LOCALDATE_FORMAT);
    }

    /**
     * 把字符串转化成LocalDate
     *
     * @param dateStr
     * @param formatter
     * @return
     */
    public static LocalDate strToLocalDate(String dateStr, DateTimeFormatter formatter) {
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 把字符串转化成LocalDateTime
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String dateStr) {
        return strToLocalDateTime(dateStr, LOCALDATETIME_FORMAT);
    }

    /**
     * 把字符串转化成LocalDateTime
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String dateStr, DateTimeFormatter formatter) {
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * 把字符串转化成Date
     *
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr, String formatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 把字符串转化成LocalTime对像
     *
     * @param localTimeStr
     * @return
     */
    public static LocalTime strToLocalTime(String localTimeStr) {
        return strToLocalTime(localTimeStr, LOCALTIME_FORMAT);
    }


    /**
     * 把字符串转化成LocalTime对像
     *
     * @param localTimeStr
     * @param formatter
     * @return
     */
    public static LocalTime strToLocalTime(String localTimeStr, DateTimeFormatter formatter) {
        return LocalTime.parse(localTimeStr, formatter);
    }


    /**
     * 把LocalDate转化成字符串
     *
     * @param localDate
     * @return
     */
    public static String dateToStr(LocalDate localDate) {
        return dateToStr(localDate, LOCALDATE_FORMAT);
    }

    /**
     * 把LocalDate转化成字符串
     *
     * @param localDate
     * @param formatter
     * @return
     */
    public static String dateToStr(LocalDate localDate, DateTimeFormatter formatter) {
        return localDate.format(formatter);
    }

    /**
     * date转字符串
     *
     * @param date       时间
     * @param dateFormat 时间格式
     * @return
     */
    public static String dateToStr(Date date, String dateFormat) {
        //date为null时返回空字符串
        if (date == null) {
            return "";
        }
        //传入的时间格式不为空时
        if (StringUtils.isNotBlank(dateFormat)) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.format(date);
        } else {
            //传入的时间格式为空时默认格式为yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
    }

    /**
     * date转字符串
     *
     * @param date 时间
     * @return
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, "");
    }


    /**
     * 把LocalDateTime转化成字符串
     *
     * @param localDateTime
     * @return
     */
    public static String dateToStr(LocalDateTime localDateTime) {
        return dateToStr(localDateTime, LOCALDATETIME_FORMAT);
    }

    /**
     * 把LocalDateTime转化成字符串
     *
     * @param localDateTime
     * @return
     */
    public static String dateToStr(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return localDateTime.format(formatter);
    }

    /**
     * @param date
     * @return
     */
    public static int getDay(LocalDateTime date) {
        return date.getDayOfMonth();
    }

    public static int getDay(LocalDate date) {
        return date.getDayOfMonth();
    }

    /**
     * 判断是否是日期
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        return isDate(date, LOCALDATE_FORMAT);
    }

    /**
     * 判断是否是时间
     *
     * @param date
     * @return
     */
    public static boolean isDateTime(String date) {
        return isDate(date, LOCALDATETIME_FORMAT);
    }

    /**
     * 判断是否是日期
     *
     * @param date
     * @param format
     * @return
     */
    public static boolean isDate(String date, DateTimeFormatter format) {
        format.toString();
        try {
            format.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

	public static boolean isDateFormat(String dateString, String format) {
		boolean result = true;
		try {
			LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(format));
		} catch (DateTimeParseException e) {
			result = false;
		}
		return result;
	}

    public static int getMonth(LocalDate date) {
        return date.getMonthValue();
    }

    public static int getMonth(LocalDateTime date) {
        return date.getMonthValue();
    }

    public static int getYear(LocalDate date) {
        return date.getYear();
    }

    public static int getYear(LocalDateTime date) {
        return date.getYear();
    }

    public static boolean eq(LocalDate date1, LocalDate date2) {
        return date1.equals(date2);
    }

    public static boolean eq(LocalDateTime date1, LocalDateTime date2) {
        return date1.equals(date2);
    }

    public static boolean ne(LocalDate date1, LocalDate date2) {
        return !date1.equals(date2);
    }

    public static boolean ne(LocalDateTime date1, LocalDateTime date2) {
        return !date1.equals(date2);
    }

    public static boolean lt(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2) < 0;
    }

    public static boolean lt(LocalDateTime date1, LocalDateTime date2) {
        return date1.compareTo(date2) < 0;
    }

    public static boolean lt(LocalTime time1, LocalTime time2) {
        return time1.compareTo(time2) < 0;
    }

    public static boolean le(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2) <= 0;
    }

    public static boolean le(LocalDateTime date1, LocalDateTime date2) {
        return date1.compareTo(date2) <= 0;
    }

    public static boolean le(LocalTime time1, LocalTime time2) {
        return time1.compareTo(time2) <= 0;
    }

    public static boolean gt(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2) > 0;
    }

    public static boolean gt(LocalDateTime date1, LocalDateTime date2) {
        return date1.compareTo(date2) > 0;
    }

    public static boolean gt(LocalTime time1, LocalTime time2) {
        return time1.compareTo(time2) > 0;
    }

    public static boolean ge(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2) >= 0;
    }

    public static boolean ge(LocalDateTime date1, LocalDateTime date2) {
        return date1.compareTo(date2) >= 0;
    }

    public static boolean ge(LocalTime time1, LocalTime time2) {
        return time1.compareTo(time2) >= 0;
    }


    public static int getCurrentTimeSecs() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long getCurrentTimeLongSecs() {
        return System.currentTimeMillis() / 1000;
    }

    public static long getCurrentTimeMicroSec() {
        return System.currentTimeMillis() * 1000;
    }

	public static float getCurrentTimeFloatSecs() {
		return System.currentTimeMillis() / 1000;
	}

	public static void main(String[] args) {
		System.out.println(getCurrentTimeFloatSecs()+"");
		System.out.println(getCurrentTimeLongSecs());

		String solarDate=solarToLunar("2022-08-09");

		String lunarDate=lunarToSolar(2023, 8, 23);
	}

    /**
     * 获取两个日期的月份数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getBetweenMonths(LocalDate startDate, LocalDate endDate) {
        Period pe = Period.between(startDate, endDate);
        return pe.getYears() * 12 + pe.getMonths();
    }

    /**
     * 获取两个日期的年份数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getBetweenYears(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate).getYears();
    }

    /**
     * 获取两个日期的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getBetweenDays(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 获取两个时间的秒数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long getBetweenSeconds(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).getSeconds();
    }

    /**
     * 获取两个时间的分钟数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long getBetweenMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        return ChronoUnit.MINUTES.between(startTime, endTime);
    }

    /**
     * 获取两个时间小时数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long getBetweenHours(LocalDateTime startTime, LocalDateTime endTime) {
        return ChronoUnit.HOURS.between(startTime, endTime);
    }

    /**
     * 添加月份
     *
     * @param startDate
     * @param month
     * @return
     */
    public static LocalDate addMonth(LocalDate startDate, int month) {
        return startDate.plusMonths(month);
    }

    /**
     * 添加月份
     *
     * @param startDate
     * @param month
     * @return
     */
    public static LocalDateTime addMonth(LocalDateTime startDate, int month) {
        return startDate.plusMonths(month);
    }

    /**
     * 获得指定日期的前一天
     *
     * @param date
     * @return
     */
    public static LocalDate getSpecifiedDayBefore(LocalDate date) {
        return date.minusDays(1L);
    }

    /**
     * 获得指定日期的后一天
     *
     * @param date
     * @return
     */
    public static LocalDate getSpecifiedDayAfter(LocalDate date) {
        return date.plusDays(1L);
    }

    /**
     * 添加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /**
     * 添加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static LocalDateTime addDays(LocalDateTime date, int days) {
        return date.plusDays(days);
    }


	public static Date addDays(Date datetime, int days) {
		return localDateToDate(dateToLocalDateTime(datetime).plusDays(days));
	}

    /**
     * 获取当前日期所在月的最后一天
     *
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return LocalDate.from(TemporalAdjusters.lastDayOfMonth().adjustInto(date));
    }

    /**
     * 获取当前日期所在月的第一天
     *
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return LocalDate.from(TemporalAdjusters.firstDayOfMonth().adjustInto(date));
    }

    /**
     * 获取当前日期所在月的最后一天
     *
     * @param date
     * @return
     */
    public static LocalDateTime getLastDayOfMonth(LocalDateTime date) {
        return LocalDateTime.from(TemporalAdjusters.lastDayOfMonth().adjustInto(date));
    }

    /**
     * 判断 compareTime 是否在 startTime，endTime 之间
     *
     * @param compareTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isBetweenTimes(LocalTime compareTime, LocalTime startTime, LocalTime endTime) {
        return compareTime.compareTo(startTime) >= 0 && (compareTime.compareTo(endTime)) <= 0;
    }

    public static boolean isBetweenTimes(LocalDateTime compareTime, LocalDateTime startTime, LocalDateTime endTime) {
        return compareTime.compareTo(startTime) >= 0 && (compareTime.compareTo(endTime)) <= 0;
    }

    /**
     * 获取最小的日期
     *
     * @param date
     * @return
     */
    public static LocalDate min(LocalDate... date) {
        if (date.length == 1) {
            return date[0];
        } else if (date.length == 2) {
            return lt(date[0], date[1]) ? date[0] : date[1];
        }
        LocalDate min = date[0];
        for (int i = 1; i < date.length; i++) {
            if (lt(date[i], min)) {
                min = date[i];
            }
        }
        return min;
    }

    /**
     * 获取最大的日期
     *
     * @param date
     * @return
     */
    public static LocalDate max(LocalDate... date) {
        if (date.length == 1) {
            return date[0];
        } else if (date.length == 2) {
            return gt(date[0], date[1]) ? date[0] : date[1];
        }
        LocalDate max = date[0];
        for (int i = 1; i < date.length; i++) {
            if (gt(date[i], max)) {
                max = date[i];
            }
        }
        return max;
    }

    /**
     * 根据字符串时间转时间戳
     *
     * @param dateStr
     * @return
     */
    public static int dateStrToTimeStamp(String dateStr, Integer offsetHours) {
        LocalDate localDate = strToLocalDate(dateStr);
        return (int) ((localDate.atStartOfDay().toInstant(ZoneOffset.ofHours(offsetHours)).toEpochMilli()) / 1000);
    }

	/**
	 * 根据字符串时间转时间戳
	 *
	 * @param dateStr 日期字符串  格式 yyyy-MM-dd
	 * @param timeZone 时区 例如: Asia/Hong_Kong
	 * @return
	 */
	public static long dateStrToTimeStamp(String dateStr, String timeZone) {
		LocalDate localDate = strToLocalDate(dateStr);
		return ((localDate.atStartOfDay().atZone(ZoneId.of(timeZone)).toInstant().toEpochMilli()) / 1000);
	}

	/**
	 * 根据字符串时间转时间戳
	 *
	 * @param dateStr
	 * @return
	 */
	public static long dateStrToLongTimeStamp(String dateStr, Integer offsetHours) {
		LocalDate localDate = strToLocalDate(dateStr);
		return ((localDate.atStartOfDay().toInstant(ZoneOffset.ofHours(offsetHours)).toEpochMilli()) / 1000);
	}

    /**
     * localDateTime转时间
     *
     * @param localDateTime
     * @param offsetHours
     * @return
     */
    public static int dateTimeToTimeStamp(LocalDateTime localDateTime, Integer offsetHours) {
        return (int) ((localDateTime.toInstant(ZoneOffset.ofHours(offsetHours)).toEpochMilli()) / 1000);
    }

	/**
	 * localDateTime转时间戳
	 * @param localDateTime
	 * @return
	 */
	public static int localDateTimeToTimeStamp(LocalDateTime localDateTime) {
    	return (int) localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
	}

    /**
     * localDate转时间
     *
     * @param localDate
     * @param offsetHours
     * @return
     */
    public static int dateToTimeStamp(LocalDate localDate, Integer offsetHours) {
        return (int) ((localDate.atStartOfDay().toInstant(ZoneOffset.ofHours(offsetHours)).toEpochMilli()) / 1000);
    }

	/**
	 * localDate转时间
	 *
	 * @param localDate
	 * @param offsetHours
	 * @return
	 */
	public static Long dateToLongTimeStamp(LocalDate localDate, Integer offsetHours) {
		return ((localDate.atStartOfDay().toInstant(ZoneOffset.ofHours(offsetHours)).toEpochMilli()) / 1000);
	}


    /**
     * Date转时间
     */
    public static int dateToTimeStamp(Date date, Integer offsetHours) {
        return (int) dateToLocalDateTime(date).toEpochSecond(ZoneOffset.ofHours(offsetHours));
    }

    /**
     * 时间戳转时间字符串
     *
     * @param timeStamp
     * @param offsetHours
     * @return
     */
    public static String timeStampToStr(Integer timeStamp, Integer offsetHours) {
        if (timeStamp == null || timeStamp <= 0) {
            return "";
        }
        return dateToStr(Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalDateTime(), LOCALDATE_FORMAT);
    }

	/**
	 * 时间戳转时间字符串
	 *
	 * @param timeStamp
	 * @param zoneId
	 * @return
	 */
	public static String timeStampToStr(Long timeStamp, ZoneId zoneId, DateTimeFormatter formatter) {
		if (timeStamp == null || timeStamp <= 0) {
			return "";
		}
		return dateToStr(Instant.ofEpochMilli(timeStamp * 1000L).atZone(zoneId).toLocalDateTime(), formatter);
	}

	/**
	 * 时间戳转时间字符串
	 *
	 * @param timeStamp
	 * @param timeZone
	 * @return
	 */
	public static String timeStampToStrWithTimeZone(Integer timeStamp, String timeZone) {
		if (timeStamp == null || timeStamp <= 0) {
			return "";
		}
		return dateToStr(Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneId.of(timeZone)).toLocalDateTime(), LOCALDATE_FORMAT);
	}

    /**
     * 时间戳转时间字符串
     *
     * @param timeStamp
     * @param offsetHours
     * @return
     */
    public static String longTimeStampToStr(Long timeStamp, Integer offsetHours, DateTimeFormatter formatter) {
        if (timeStamp == null || timeStamp <= 0) {
            return "";
        }
        return dateToStr(Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalDateTime(), formatter);
    }

    public static String timeStampToStrWithTimeZone(Long timeStamp, String timeZone, DateTimeFormatter formatter) {
        if (timeStamp == null || timeStamp <= 0) {
            return "";
        }
        return dateToStr(Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneId.of(timeZone)).toLocalDateTime(), formatter);
    }

    /**
     * 时间戳转localDateTime
     *
     * @param timeStamp
     * @param offsetHours
     * @return
     */
    public static LocalDateTime timeStampToLocalDateTime(Integer timeStamp, Integer offsetHours) {
        return Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalDateTime();
    }

    public static LocalDateTime timeStampToLocalDateTime(Long timeStamp, Integer offsetHours) {
        return Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalDateTime();
    }

    public static LocalDateTime timeStampToLocalDateTime(Long timeStamp) {
        return timeStampToLocalDateTime(timeStamp, 8);
    }

    public static LocalDateTime timeStamp2LocalDateTime(Long timeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), ZoneId.systemDefault());
    }

	public static LocalDateTime timeStampToLocalDateTimeWithTimeZone(Long timeStamp, String timeZone) {
		return Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneId.of(timeZone)).toLocalDateTime();
	}

    /**
     * 时间戳转localDate
     *
     * @param timeStamp
     * @param offsetHours
     * @return
     */
    public static LocalDate timeStampToLocalDate(Integer timeStamp, Integer offsetHours) {
        return Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalDate();
    }

    /**
     * 时间戳转localDate
     *
     * @param timeStamp
     * @param offsetHours
     * @return
     */
    public static LocalTime timeStampToLocalTime(Integer timeStamp, Integer offsetHours) {
        return Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalTime();
    }

    /**
     * 获取距离指定时间半年后的时间戳(183天)
     *
     * @return
     */
	public static int getHalfYearLaterTimeSecs(int timeStamp) {
        return timeStamp + 24 * 60 * 60 * 183;
    }

    public static int getTwoYearLaterTimeSecs(int timeStamp) {
        return timeStamp + 24 * 60 * 60 * 730;
    }

    /**
     * 获取指定天数间隔的时间戳
     *
     * @param timeStamp
     * @param duration
     * @return
     */
    public static int getTargetDayDurationTimeSecs(int timeStamp, int duration) {
        return timeStamp + 24 * 60 * 60 * duration;
    }

    /**
     * 获取指定月份之后的时间戳
     *
     * @param localDateTime
     * @param month
     * @return
     */
    public static int getTimeStampAfterTargetMonth(LocalDateTime localDateTime, Integer month) {
        return dateTimeToTimeStamp(localDateTime.plusMonths(month), 8);
    }

    /**
     * @param timeStamp
     * @param offsetHours
     * @param dateTimeFormatter
     * @return
     */
    public static String timeStampToTargetFormat(Integer timeStamp, Integer offsetHours, DateTimeFormatter dateTimeFormatter) {
        if (timeStamp == null || timeStamp <= 0) {
            return "";
        }
        return dateToStr(Instant.ofEpochMilli(timeStamp * 1000L).atZone(ZoneOffset.ofHours(offsetHours)).toLocalDateTime(), dateTimeFormatter);
    }

    /**
     * 根据字符串 YYYY-MM-dd hh:mm 转时间戳
     *
     * @param dateStr
     * @param offsetHours
     * @return
     */
    public static int dateTimeHourStrToTimeStamp(String dateStr, Integer offsetHours) {
        LocalDateTime localDateTime = strToLocalDateTime(dateStr, LOCALDATETIME_FORMAT1);
        return dateTimeToTimeStamp(localDateTime, offsetHours);
    }

    public static String getCurrentDayStr() {
        return dateToStr(LocalDate.now(), LOCALDATE_FORMAT);
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getCurrentDateTimeStr() {
        return dateToStr(LocalDateTime.now(), LOCALDATETIME_FORMAT);
    }

    /**
     * 获取两个日期字符串之间的日期集合
     *
     * @param startTime:String
     * @param endTime:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        // 转化成日期类型
        LocalDate startDate = LocalDate.parse(startTime, LOCALDATE_FORMAT);
        LocalDate endDate = LocalDate.parse(endTime, LOCALDATE_FORMAT);

        while (!startDate.isAfter(endDate)) {
            // 把日期添加到集合
            list.add(startDate.format(LOCALDATE_FORMAT));
            startDate = startDate.plusDays(1);
        }
        return list;
    }

    public static List<String> getBetweenDateByTimeStamp(Integer startTime, Integer endTime) {
        String startTimeStr = timeStampToStr(startTime, 8);
        String endTimeStr = timeStampToStr(endTime, 8);
        return getBetweenDate(startTimeStr, endTimeStr);
    }

    /**
     * 获取一天开始时间戳(yyyy-MM-dd)
     *
     * @param startTime
     * @return
     */
    public static Integer getBeginOfDayTimeStamp(String startTime) {
        LocalDate startDate = LocalDate.parse(startTime, LOCALDATE_FORMAT);
        return dateTimeToTimeStamp(startDate.atStartOfDay(), 8);
    }

    /**
     * 获取一天结束时间戳(yyyy-MM-dd)
     *
     * @param endTime
     * @return
     */
    public static Integer getEndOfDayTimeStamp(String endTime) {
        LocalDate endDate = LocalDate.parse(endTime, LOCALDATE_FORMAT);
        return dateTimeToTimeStamp(LocalDateTime.of(endDate, LocalTime.MAX), 8);
    }

    /**
     * 获取一天结束的时间戳(参数为localDateTime)
     *
     * @param localDateTime
     * @return
     */
    public static Integer getEndOfDayTimeStampByLocalDateTime(LocalDateTime localDateTime) {
        return dateTimeToTimeStamp(LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX), 8);
    }

    /**
     * 将 LocalDate 转为 Date
     *
     * @param localDate
     * @return java.helper.Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 LocalDateTime 转为 Date
     *
     * @param localDateTime 时间
     * @return java.helper.Date
     */
    public static Date localDateToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转换成LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转换成LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param begin 开始日期
     * @param end   结束日期
     * @return 开始与结束之间的所以日期，包括起止
     */
    public static List<LocalDate> getMiddleDate(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = new ArrayList<>();
        long length = end.toEpochDay() - begin.toEpochDay();
        for (long i = length; i >= 0; i--) {
            localDateList.add(end.minusDays(i));
        }
        return localDateList;
    }

    /**
     * 获取中间的那个日期
     *
     * @param begin
     * @param end
     * @return
     */
    /*public static String getMiddleDateStr(String begin, String end) {
        int beginStamp = strToTimeStamp(begin, 8);
        int endStamp = strToTimeStamp(end, 8);

        int middleStamp = beginStamp + (endStamp - beginStamp) / 2;
        return timeStampToTargetFormat(middleStamp, 8, LOCALDATE_FORMAT);
    }*/

    /**
     * 获取下一个半小时的时间
     */
    public static LocalDateTime getNextHalfHoursTime() {
        long now = getCurrentTimeLongSecs();
        long time = (now - now % SECONDS_IN_HALF_HOUR) + SECONDS_IN_HALF_HOUR;
        return timeStampToLocalDateTime(time, 8);
    }

    /**
     * 时间 String 转为 时间戳
     * @param str
     * @param formatter
     * @return
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static long parserToTime(String str, DateTimeFormatter formatter) {
        return LocalDateTime.parse(str, formatter).toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 当前时间是今天之前
     */
    public static boolean isBeforeToday(String time, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime current = LocalDateTime.parse(time, dateTimeFormatter);
        LocalDateTime today = LocalDateTime.parse(LocalDateTime.now().format(LOCALDATE_FORMAT) + " 00:00:00", LOCALDATETIME_FORMAT);
        return today.compareTo(current) > 0;
    }

    /**
     * 按时区解析一个时间字符串
     *
     * @param timeStr    时间字符串
     * @param timeZoneId 时区ID如： Asia/Shanghai
     * @return 返回日期对象
     */
    public static ZonedDateTime parseTimezoneDate(String timeStr, String timeZoneId) throws ParseException {
        ZoneId timeZone = ZoneId.of(timeZoneId);
        return ZonedDateTime.of(LocalDateTime.parse(timeStr, LOCALDATETIME_FORMAT), timeZone);
    }

    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 获取传入日期是周几，周日是7
     * @param timeStamp
     * @param formatter
     * @return
     */
    public static Integer getWeekDay(Long timeStamp, String formatter) {
        return DateTimeUtils.timeStampToLocalDateTimeWithTimeZone(timeStamp, formatter).getDayOfWeek().getValue();
    }

	/**
	 * 注意: 本方法是默认认为传进来的时间是当前系统默认时区的时间
	 * 转换成目标时区的时间
	 * @param timeZone   America / New_York  或 Etc/GMT
	 * @param from  当前系统默认时区的时间
	 * @return
	 */
	public static LocalDateTime changeTimeZone(LocalDateTime from, String timeZone) {
		return from.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(timeZone)).toLocalDateTime();
	}

	/**
	 * 转换成目标时区的时间
	 * @param fromTimeZone   America / New_York  或 Etc/GMT
	 * @return
	 */
	public static LocalDateTime changeTimeZone(LocalDateTime from, String fromTimeZone, String toTimeZone) {
		return from.atZone(ZoneId.of(fromTimeZone)).withZoneSameInstant(ZoneId.of(toTimeZone)).toLocalDateTime();
	}

	/**
	 * 比较
	 * 00:12  05:22 这类时间的大小
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean gt(String start, String end) {
		String currentDayStr = DateTimeUtils.getCurrentDayStr();
		String startDateStr = currentDayStr + " " + start;
		String endDateStr = currentDayStr + " " + end;
		LocalDate startDate = DateTimeUtils.strToLocalDate(startDateStr, DateTimeUtils.LOCALDATETIME_FORMAT1);
		LocalDate endDate = DateTimeUtils.strToLocalDate(endDateStr, DateTimeUtils.LOCALDATETIME_FORMAT1);
		return gt(startDate, endDate);
	}

	public static Long localDateToTimeStampWithTimeZone(LocalDate motExpired, String timeZone) {
		return motExpired.atStartOfDay(ZoneId.of(timeZone)).toEpochSecond();
	}

	/**
	 * 公历转农历
	 * @param date
	 * @return
	 */
	public static String solarToLunar(String date){
		ChineseDate chineseDate=new ChineseDate(DateUtil.parseDate(date));
		String lunar=chineseDate.toStringNormal();

		System.out.println("获得农历年份: " + chineseDate.getChineseYear()); // 获得农历年份, 如: 2020
		System.out.println("获取农历的月，从1开始计数: " + chineseDate.getMonth()); // 获取农历的月，从1开始计数
		System.out.println("当前农历月份是否为闰月: " + chineseDate.isLeapMonth()); // 当前农历月份是否为闰月
		System.out.println("获得农历月份（中文，例如二月，十二月，或者润一月）: " + chineseDate.getChineseMonth()); // 获得农历月份（中文，例如二月，十二月，或者润一月）
		System.out.println("获得农历月称呼（中文，例如二月，腊月，或者润正月）: " + chineseDate.getChineseMonthName()); // 获得农历月称呼（中文，例如二月，腊月，或者润正月）

		System.out.println("获取农历的日，从1开始计数: " + chineseDate.getDay()); // 获取农历的日，从1开始计数
		System.out.println("获得农历日: " + chineseDate.getChineseDay()); // 获得农历日
		System.out.println(chineseDate.toStringNormal()); // 转换为标准的日期格式来表示农历日期，例如2020-01-13

		// -----------------------------------------------------------------------
		System.out.println("获取节日: " + chineseDate.getFestivals()); // 获取节日
		System.out.println("获得年份生肖: " + chineseDate.getChineseZodiac()); // 获得年份生肖
		System.out.println("获得年的天干地支: " + chineseDate.getCyclical()); // 获得年的天干地支
		System.out.println("干支纪年信息: " + chineseDate.getCyclicalYMD()); // 干支纪年信息
		System.out.println("获得节气: " + chineseDate.getTerm()); // 获得节气
		System.out.println("转换为标准的日期格式来表示农历日期: " + chineseDate.toStringNormal()); // 转换为标准的日期格式来表示农历日期，例如2020-01-13
		System.out.println(chineseDate.toString());
		return lunar;
	}

	/**
	 * 农历转公历
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String lunarToSolar(int year, int month, int day){
		ChineseDate chineseDate=new ChineseDate(year, month, day);
		String gregorian=chineseDate.getGregorianDate().toString();
		System.out.println(gregorian);
		System.out.println("获取公历的年份: " + chineseDate.getGregorianYear()); // 获取公历的年份
		System.out.println("获取公历的月，从1开始计数: " + chineseDate.getGregorianMonthBase1()); // 获取公历的月，从1开始计数
		System.out.println("获取公历的月，从0开始计数: " + chineseDate.getGregorianMonth()); // 获取公历的月，从0开始计数
		System.out.println("获取公历的日: " + chineseDate.getGregorianDay()); // 获取公历的日
		System.out.println("获取公历的Date: " + chineseDate.getGregorianDate()); // 获取公历的Date
		System.out.println("获取公历的Calendar: " + chineseDate.getGregorianCalendar()); // 获取公历的Calendar
		System.out.println("获取节日: " + chineseDate.getFestivals()); // 获取节日
		System.out.println("获得年份生肖: " + chineseDate.getChineseZodiac()); // 获得年份生肖
		System.out.println("获得年的天干地支: " + chineseDate.getCyclical()); // 获得年的天干地支
		System.out.println("干支纪年信息: " + chineseDate.getCyclicalYMD()); // 干支纪年信息
		System.out.println("获得节气: " + chineseDate.getTerm()); // 获得节气
		System.out.println("转换为标准的日期格式来表示农历日期: " + chineseDate.toString()); // 转换为标准的日期格式来表示农历日期，例如2020-01-13
		System.out.println(chineseDate.toString());

		return gregorian;
	}

}
