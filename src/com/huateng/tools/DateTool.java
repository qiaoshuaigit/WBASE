package com.huateng.tools;

/**
 * <p>Title: </p>
 * <p>Description:取得系统当前时间和格式化时间字符串 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */
/**
 <p>** <p>调用</p>                                                       </p>
 <p>*例1：取得系统当前时间YYYYMMDDhhmmss                                 </p>
 <p>**  FormatDate.getDateTime());                                       </p>
 <p>*                                                                    </p>
 <p>*例2：取得系统当前时间hhmmss                                         </p>
 <p>**  FormatDate.getTime();                                            </p>
 <p>*                                                                    </p>
 <p>*例3：取得系统当前时间YYYYMMDD                                       </p>
 <p>**  FormatDate.getDate();                                            </p>
 <p>*                                                                    </p>
 <p>*例4：格式化时间 如:YYYYMMDDhhmmss 格式化为YYYY-MM-DD hh:mm:ss;      </p>
 <p>*	第一个参数代表：需格式化的字符串；                               </p>
 <p>*	第二个参数代表：格式化的分隔符；可以用“-” or "/";                </p>
 <p>**  FormatDate.formatDateTime(getDateTime(),"-");                    </p>
 <p>*                                                                    </p>
 <p>*例5：格式化时间 如:YYYYMMDD 格式化为YYYY-MM-DD ;                    </p>
 <p>**  FormatDate.formatDate(getDateTime(),"-"));                       </p>
 <p>*                                                                    </p>
 <p>*例6：格式化时间 如:hhmmss 格式化为hh:mm:ss;                         </p>
 <p>**  FormatDate.formatTime(getDateTime());                            </p>
 **/

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 
 * @version $Revision: 1.1 $
 * 建立日期 2008-11-6 
 */
public class DateTool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6717597172099897804L;

	/**
	 * 按照格式获取当前时间
	 * 
	 * @param format
	 * @return Date
	 */
	public static Date getCurrentDateTime(String format) {
		String sDate = getDateTime(format);
		try {
			return (new SimpleDateFormat(format)).parse(sDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return Date
	 */
	public static Date getCurrentDateTime2() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * 按指定格式取系统当前时间字符串
	 * 
	 * @param format
	 *            String
	 * @return String
	 */
	public static String getDateTime(String format) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		String strDate = sdf.format(new java.util.Date());

		return strDate;
	}
	
	/**
	 * HHmmssSSS
	 * @return
	 */
	public static String getEsbSndTm() {
		return getDateTime("HHmmssSSS");
	}

	/**
	 * 按默认格式取系统当前时间， 默认格式为：yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getDateTime() {
		return getDateTime("yyyyMMddHHmmss");
	}


	/**
	 * 取得系统当前时间,yyyyMMdd
	 * @return String
	 */
	public static String getDate() {
		return getDateTime("yyyyMMdd");
	}
	/**
	 * 获取发送核心系统日期,yyyy-MM-dd
	 * @return
	 */
	public static String getTOCBSDATE(){
		return getDateTime("yyyy-MM-dd");
	}
	/**
	 * 获取发送核心系统时间,HH.mm.ss
	 * @return
	 */
	public static String getTOCBSTIME(){
		return getDateTime("HH.mm.ss");
	}

	/**
	 * 取得系统当前时间，HHmmss
	 * @return String
	 */
	public static String getTime() {
		return getDateTime("HHmmss");
	}
	
	/**
	 * HHmmss -> HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String formatTime(String time) {
		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
	}

	/**
	 * yyyyMMdd->yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String formatDate(String date){
		return date.substring(0,4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
	}
	
	/**
	 * 是否为合法的日期字符串
	 * 
	 * @param strDate
	 *            String
	 * @param format
	 *            String
	 * @return boolean
	 */
	public static boolean isValidDate(String strDate, String format) {
		boolean islegal = false;

		try {
			String newDate = date2Str(str2Date(strDate, format), format);

			if (newDate.equals(strDate)) {
				islegal = true;
			}
		} catch (Exception e) {
			return false;
		}

		return islegal;
	}

	/***************************************************************************
	 * / 把时间转换为字串 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            待转换的时间
	 * @return String
	 */
	public static String date2Str(Date date) {
		String format = "yyyy-MM-dd HH:mm:ss";
		return date2Str(date, format);
	}

	/**
	 * 把时间转换为字串
	 * 
	 * @param date
	 *            待转换的时间
	 * @param format
	 *            转换格式
	 * @return String
	 */
	public static String date2Str(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 把时间格式由一种格式转换为另一种格式为字串, 旧格式默认为 "yyyyMMddHHmmss"
	 * 
	 * @param dateStr
	 *            String 日期字符串
	 * @param newFormat
	 *            String 新格式
	 * @return String
	 */
	public static String formatDateTime(String dateStr, String newFormat) {
		return formatDateTime(dateStr, newFormat, "yyyyMMddHHmmss");
	}

	/**
	 * 把时间格式由一种格式转换为另一种格式为字串
	 * 
	 * @param dateStr
	 *            String 日期字符串
	 * @param newFormat
	 *            String 新格式
	 * @param oldFormat
	 *            String 旧格式
	 * @return String
	 */
	public static String formatDateTime(String dateStr, String newFormat,
			String oldFormat) {
		Date date = str2Date(dateStr, oldFormat);
		return date2Str(date, newFormat);
	}

	/**
	 * 把字串转换为日期
	 * 
	 * @param sDate
	 *            字串形式的日期
	 * @param format
	 *            字串格式
	 * @return 转换为日期类型
	 */
	public static Date str2Date(String sDate, String format) {
		try {
			return (new SimpleDateFormat(format)).parse(sDate);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 取某一日期增减 n 值后的日期, n 由 dateField 决定是年、月、日 根据增加or减少的时间得到新的日期
	 * 
	 * @param date
	 *            Date 参照日期
	 * @param counts
	 *            int 增减的数值
	 * @param dateField
	 *            int 需操作的日期字段, 取值范围如下 Calendar.YEAY 年 Calendar.MONTH 月
	 *            Calendar.DATE 日 .... Calendar.SECOND 秒
	 * @return Date
	 */
	public static Date addDate(Date date, int counts, int dateField) {
		GregorianCalendar curGc = new GregorianCalendar();

		if (date != null)
			curGc.setTime(date);

		curGc.add(dateField, counts);

		return curGc.getTime();
	}

	/**
	 * 将日期增减 n 天
	 * 
	 * @param date
	 *            Date 参照日期,如果为null则取当前日期
	 * @param days
	 *            int 增减的天数
	 * @return Date
	 */
	public static Date addDate(Date date, int days) {
		return addDate(date, days, Calendar.DATE);
	}

	/**
	 * 将字符串型日期增减 n 天
	 * 
	 * @param date
	 *            String
	 * @param days
	 *            int
	 * @param format
	 *            String
	 * @return String
	 */
	public static String addDate(String date, int days, String format) {
		return date2Str(addDate(str2Date(date, format), days, Calendar.DATE),
				format);
	}

	/**
	 * 将日期增加 n 个月
	 * 
	 * @param date
	 *            Date
	 * @param months
	 *            int
	 * @return Date
	 */
	public static Date addMonth(Date date, int months) {
		return addDate(date, months, Calendar.MONTH);
	}

	/**
	 * 将字符串型日期增加 n 个月
	 * 
	 * @param date
	 *            String
	 * @param months
	 *            int
	 * @param format
	 *            String
	 * @return String
	 */
	public static String addMonth(String date, int months, String format) {
		return date2Str(
				addDate(str2Date(date, format), months, Calendar.MONTH), format);
	}

	/**
	 * 取得月最后一天 先取得下月月首,再减一,得月末
	 * 
	 * @param date
	 *            String
	 * @return String
	 */
	public static String lastDateOfMonth(String date) {
		return date2Str(addDate(addDate(str2Date(date.substring(0, 6) + "01",
				"yyyyMMdd"), 1, Calendar.MONTH), -1, Calendar.DATE), "yyyyMMdd");
	}

	/**
	 * 得到星期
	 * 
	 * @param date
	 *            String
	 * @return String
	 */
	public static String getWeekDay(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("E");
		return formatter.format(str2Date(date, "yyyyMMdd"));
	}

	public static Date getStartDateByDays(Date endDate, int days) {
        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);
        calendarEndDate.add(Calendar.DAY_OF_YEAR, 0 - days);

        return calendarEndDate.getTime();
    }
}
