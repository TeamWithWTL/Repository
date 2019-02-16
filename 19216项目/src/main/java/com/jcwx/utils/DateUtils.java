package com.jcwx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Gs(2018-5-22)
 * 日期工具类
 */
@SuppressWarnings("static-access")
public class DateUtils {
	
	public static String formate = "yyyy-MM-dd HH:mm:ss";
	public static String formate1 = "yyyyMMddHHmmss";

	/**
	 * 日期格式化
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String formateDate(Date date, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		if (null == date) {
			return "";
		}
		return sdf.format(date);

	}

	/**
	 * 字符串转日期
	 * @param dateString
	 * @param formate
	 * @return
	 */
	public static Date parseDate(String dateString, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		if (null == dateString || "".equals(dateString)) {
			return null;
		}
		try {
			return sdf.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前日期时间
	 * @param formate
	 * @return
	 */
	public static String getNow(String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(new Date());
	}

	/**
	 * 得到某年某月的第一天
	 * @param year
	 * @param month
	 * @param fmt
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month, String fmt) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat(fmt).format(cal.getTime());
	}

	/**
	 * 得到某年某月的最后一天
	 * @param year
	 * @param month
	 * @param fmt
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month, String fmt) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(fmt).format(cal.getTime());
	}

	/**
	 * 获取指定日期是星期几
	 * @param date
	 * @param fmt
	 * @return
	 */
	public static String getWeekOfDate(String date, String fmt) {
		String[] weekOfDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseDate(date, fmt));
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}

	/**
	 * 获取两个日期之间的日期
	 * @param startDate
	 * @param endDate
	 * @param fmt
	 * @return 日期列表（包含开始日期和结束日期）
	 */
	public static List<String> getDatesBetween2Days(String startDate, String endDate, String fmt) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(parseDate(startDate, fmt));
		endCalendar.setTime(parseDate(endDate, fmt));
		List<String> dateList = new ArrayList<String>();
		dateList.add(startDate);
		while (true) {
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
			if (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
				dateList.add(formateDate(startCalendar.getTime(), fmt));
			} else {
				break;
			}
		}
		dateList.add(endDate);
		return dateList;
	}

	/**
	 * 获取日期的下一天
	 * @param dateString
	 * @param formate
	 * @return
	 */
	public static Date nextDate(String dateString, String formate) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(formate).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		String dayAfter = new SimpleDateFormat(formate).format(c.getTime());
		return parseDate(dayAfter, formate);
	}
	
	/**
	 * 获取某年某月的第一天的 0时0分0秒0毫秒
	 * @return
	 */
	public static Calendar getMonthFisrt(String yearStr, String monthStr){
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		calendar.set(calendar.YEAR, Integer.valueOf(yearStr));									// 该年
		calendar.set(calendar.MONTH, Integer.valueOf(monthStr)-1);								// 该月
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMinimum(calendar.DAY_OF_MONTH));	// 该月第一天
		calendar.set(calendar.HOUR_OF_DAY, 0);													// 0时
		calendar.set(calendar.MINUTE, 0);														// 0分
		calendar.set(calendar.SECOND, 0);														// 0秒
		calendar.set(calendar.MILLISECOND, 0);													// 0毫秒
		return calendar;
	}
	
	/**
	 * 获取某年某月的最后一天的 0时0分0秒0毫秒
	 * @return
	 */
	public static Calendar getMonthLast(String yearStr, String monthStr){
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		calendar.set(calendar.YEAR, Integer.valueOf(yearStr));									// 该年
		calendar.set(calendar.MONTH, Integer.valueOf(monthStr)-1);								// 该月
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));	// 该月最后一天
		calendar.set(calendar.HOUR_OF_DAY, 23);													// 23时
		calendar.set(calendar.MINUTE, 59);														// 59分
		calendar.set(calendar.SECOND, 59);														// 59秒
		calendar.set(calendar.MILLISECOND, 999);												// 999毫秒
		return calendar;
	}
	
	/**
	 * 获取本月的第一天的 0时0分0秒0毫秒
	 * @return
	 */
	public static Calendar getNowMonthFisrt(){
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMinimum(calendar.DAY_OF_MONTH));	// 该月第一天
		calendar.set(calendar.HOUR_OF_DAY, 0);													// 0时
		calendar.set(calendar.MINUTE, 0);														// 0分
		calendar.set(calendar.SECOND, 0);														// 0秒
		calendar.set(calendar.MILLISECOND, 0);													// 0毫秒
		return calendar;
	}
	
	/**
	 * 获取本月的最后一天的 23时59分59秒999毫秒
	 * @return
	 */
	public static Calendar getNowMonthLast(){
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));	// 该月最后一天
		calendar.set(calendar.HOUR_OF_DAY, 23);													// 23时
		calendar.set(calendar.MINUTE, 59);														// 59分
		calendar.set(calendar.SECOND, 59);														// 59秒
		calendar.set(calendar.MILLISECOND, 999);												// 999毫秒
		return calendar;
	}
	
	/**
	 * 该月周六周天的天数
	 * @return
	 */
	public static int getMonthSundayAndStaturday(String yearStr, String monthStr){
		int days = 0;
		int maxMonthDay = 0;
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		if(null != yearStr && null != monthStr){
			calendar.set(calendar.YEAR, Integer.valueOf(yearStr));									// 该年
			calendar.set(calendar.MONTH, Integer.valueOf(monthStr)-1);								// 该月
			calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMinimum(calendar.DAY_OF_MONTH));	// 该月第一天
			maxMonthDay = calendar.getActualMaximum(calendar.DAY_OF_MONTH);							// 该月天数
		}else{
			calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMinimum(calendar.DAY_OF_MONTH));	// 本月第一天
			maxMonthDay = calendar.getActualMaximum(calendar.DAY_OF_MONTH);							// 本月天数
		}
		if(maxMonthDay > 0){
			for(int i = 0; i<maxMonthDay; i++){
				calendar.add(calendar.DAY_OF_MONTH, 1);
				if(calendar.get(calendar.DAY_OF_WEEK) == calendar.SUNDAY || calendar.get(calendar.DAY_OF_WEEK) == calendar.SATURDAY){
					days = days + 1;
				}
//				System.out.println("日期：" + calendar.get(calendar.DAY_OF_MONTH) + "==week------->" + calendar.get(calendar.DAY_OF_WEEK));
			}
		}
		return days;
	}
}
