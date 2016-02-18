package net.micode.notes.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * @ClassName: DateTimeUtils
 * @Description: TODO
 * @author: wht
 * @date:2015年1月22日 下午1:49:37
 */
@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {
	/** 完整年月日时间格式 **/
	public static final String DATE_TIME_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 短年月日格式，无中间符号 **/
	public static final String DATE_TIME_SHORT_FORMAT = "yyyyMMdd";

	/** 短年月日格式，有中间符号 **/
	public static final String DATE_TIME_SHORT_RORMAT2 = "yyyy-MM-dd";

	/**
	 * @description: 系统日期格式工具 "yyyyMMdd"
	 * @return
	 * @author: wht
	 * @date: 2015年3月11日 下午3:06:31
	 */
	public static String getDateTime2DigitLibrarySignFormat() {
		return getDateTime(DATE_TIME_SHORT_FORMAT);
	}

	/**
	 * @description: 日期格式化 获取系统时间
	 * @return 返回yyyy-MM-dd HH:mm:ss 格式字符串
	 * @author: wht
	 * @date: 2015-3-11 下午2:49:20
	 */
	public static String getDateTime() {
		// 当前时间与1970-1-1号时间差，单位毫秒
		long time = System.currentTimeMillis();
		// 格式化为yyyy-MM-dd HH:mm:ss格式
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FULL_FORMAT);
		Date date = new Date(time);
		return sdf.format(date);
	}

	/**
	 * @description: 获得系统时间，转换为指定格式
	 * @param format
	 * @return
	 * @author: wht
	 * @date: 2015-3-11 下午2:55:34
	 */
	public static String getDateTime(String format) {
		// 当前时间与1970-1-1号时间差，单位毫秒
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(time);
		return sdf.format(date);
	}

	/**
	 * @description: 按指定格式转换日期
	 * @param format
	 * @return
	 * @author: wht
	 * @date: 2015-3-11 下午2:55:34
	 */
	public static String getDateTime(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String FormatDate(String date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date1 = new Date(Long.parseLong(date));
		return formatter.format(date1);
	}

	/**
	 * @Title: getDateTime
	 * @Description: 获取系统时间
	 * @param @param format
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static Date getDate() {
		// 当前时间与1970-1-1号时间差，单位毫秒
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		return date;
	}

	/**
	 * @description: 字符串转换为日期格式（支持长日期格式，短日期格式，Long日期格式）
	 * @param strDate
	 * @return
	 * @throws ParseException
	 * @author: liutf
	 * @date: 2015年3月3日 下午5:42:29
	 */
	public static Date stringToDate(String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FULL_FORMAT,
				Locale.CHINA);
		SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_TIME_SHORT_RORMAT2,
				Locale.CHINA);
		Date rtDate = null;
		if (strDate != null && strDate.length() > 0) {
			if (strDate.length() == 10) {
				rtDate = sdf2.parse(strDate);
			} else if (strDate.length() == 19) {
				rtDate = sdf.parse(strDate);
			} else if (strDate.length() == 13) {
				rtDate = new Date(Long.parseLong(strDate));
			}
		}
		return rtDate;
	}

}
