package fun.modelbook.boxssoserver.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author jory
 * @date 2018/3/28.
 */
public class DateUtil {

    private static final Logger LOGGER = LogManager.getLogger(DateUtil.class.getName());

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";

    private static final String CHINESE_DATE_FORMAT_M_D = "yyyy年M月d日";

    private static final long COEFFICIENT = 1000L;

    private static final String[] POSSIBLE_IOS_DATE_FORMATS = {
            /* RFC 1123 with 2-digit Year */"EEE, dd MMM yy HH:mm:ss z",
            /* RFC 1123 with 4-digit Year */"EEE, dd MMM yyyy HH:mm:ss z",
            /* RFC 1123 with no Timezone */"EEE, dd MMM yy HH:mm:ss",
            /* Variant of RFC 1123 */"EEE, MMM dd yy HH:mm:ss",
            /* RFC 1123 with no Seconds */"EEE, dd MMM yy HH:mm z",
            /* Variant of RFC 1123 */"EEE dd MMM yyyy HH:mm:ss",
            /* RFC 1123 with no Day */"dd MMM yy HH:mm:ss z",
            /* RFC 1123 with no Day or Seconds */"dd MMM yy HH:mm z",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ssZ",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss'Z'",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:sszzzz",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss z",
            /* ISO 8601 */"yyyy-MM-dd'T'HH:mm:ssz",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss.SSSz",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HHmmss.SSSz",
            /* ISO 8601 slightly modified */"yyyy-MM-dd'T'HH:mm:ss",
            /* ISO 8601 w/o seconds */"yyyy-MM-dd'T'HH:mmZ",
            /* ISO 8601 w/o seconds */"yyyy-MM-dd'T'HH:mm'Z'",
            /* RFC 1123 without Day Name */"dd MMM yyyy HH:mm:ss z",
            /* RFC 1123 without Day Name and Seconds */"dd MMM yyyy HH:mm z",
            /* Simple Date Format */"yyyy-MM-dd",
            /* Simple Date Format */"MMM dd, yyyy"};

    private static final TimeZone TIMEZONE = TimeZone.getDefault();

    /**
     * 将日期格式化为：yyyy-MM-dd
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String format2Date(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    /**
     * 将日期格式化为：yyyy-MM-dd
     *
     * @param time 日期对象
     * @return 日期字符串
     */
    public static String formatLong2DateStr(Long time) {
        Date itemDate = new Date(time);
        return new SimpleDateFormat(DATE_FORMAT).format(itemDate);
    }

    /**
     * 将日期格式化为：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String format2DateTime(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    /**
     * 将日期格式化为:yyyy年MM月dd日
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String format2ChineseDate(Date date) {
        return new SimpleDateFormat(CHINESE_DATE_FORMAT).format(date);
    }

    /**
     * 将日期格式化为:yyyy年M月d日
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String format2ChineseDateMD(Date date) {
        return new SimpleDateFormat(CHINESE_DATE_FORMAT_M_D).format(date);
    }

    /**
     * 将日期转为当天的结束时间
     *
     * @param date 日期对象
     * @return date对象
     */
    public static Date trans2DateEnd(Date date) {

        Date resultDate = date;
        if (resultDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            resultDate = calendar.getTime();
        }
        return resultDate;
    }

    /**
     * 将日期转为当天的开始时间
     *
     * @param date
     * @return date对象
     */
    public static Date trans2DateStart(Date date) {
        Date resultDate = date;
        if (resultDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            resultDate = calendar.getTime();
        }
        return resultDate;
    }

    /**
     * 将日期格式字符串转换为日期对象，日期字符串格式需要和format格式相匹配
     * 日期格式字符串格式为：yyyy-MM-dd
     *
     * @param dateStr 日期格式字符串
     * @return date对象
     */
    public static Date parseDateFormatStr2Date(String dateStr) {

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将日期格式字符串转换为日期对象，日期字符串格式需要和format格式相匹配
     * 日期格式字符串格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr 日期格式字符串
     * @return date对象
     */
    public static Date parseDateTimeFormatStr2Date(String dateStr) {

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将日期格式字符串转换为日期对象，日期字符串格式需要和format格式相匹配
     * 日期格式字符串格式为：yyyy年MM月dd日
     *
     * @param dateStr 日期格式字符串
     * @return date对象
     */
    public static Date parseChineseDateFormatStr2Date(String dateStr) {

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将日期格式字符串转换为日期对象，日期字符串格式需要和format格式相匹配
     * 日期格式字符串格式为：yyyy年M月d日
     *
     * @param dateStr 日期格式字符串
     * @return date对象
     */
    public static Date parseChineseDateFormatMDStr2Date(String dateStr) {

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将Unix时间戳格式化为日期格式字符串：yyyy-MM-dd
     *
     * @param timestamp Unix时间戳（精确到秒数）
     * @return 日期字符串
     */
    public static String formatUnixTimeStamp2DateStr(Long timestamp) {

        return new SimpleDateFormat(DATE_FORMAT).format(new Date(timestamp * COEFFICIENT));
    }

    /**
     * 将Unix时间戳格式化为日期格式字符串：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp Unix时间戳（精确到秒数）
     * @return 日期字符串
     */
    public static String formatUnixTimeStamp2DateTimeStr(Long timestamp) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date(timestamp * COEFFICIENT));
    }

    /**
     * 将Unix时间戳格式化为日期格式字符串:yyyy年MM月dd日
     *
     * @param timestamp Unix时间戳（精确到秒数）
     * @return 日期字符串
     */
    public static String formatUnixTimeStamp2ChineseDateStr(Long timestamp) {
        return new SimpleDateFormat(CHINESE_DATE_FORMAT).format(new Date(timestamp * COEFFICIENT));
    }

    /**
     * 将Unix时间戳格式化为日期格式字符串:yyyy年M月d日
     *
     * @param timestamp Unix时间戳（精确到秒数）
     * @return 日期字符串
     */
    public static String formatUnixTimeStamp2ChineseDateMDStr(Long timestamp) {
        return new SimpleDateFormat(CHINESE_DATE_FORMAT_M_D).format(new Date(timestamp * COEFFICIENT));
    }

    /**
     * 将时间戳格式化为日期格式字符串：yyyy-MM-dd
     *
     * @param timestamp 时间戳（精确到毫秒数）
     * @return 日期字符串
     */
    public static String formatTimeStamp2DateStr(Long timestamp) {

        return new SimpleDateFormat(DATE_FORMAT).format(new Date(timestamp));
    }

    /**
     * 将时间戳格式化为日期格式字符串：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳（精确到毫秒数）
     * @return 日期字符串
     */
    public static String formatTimeStamp2DateTimeStr(Long timestamp) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date(timestamp));
    }

    /**
     * 将时间戳格式化为日期格式字符串:yyyy年MM月dd日
     *
     * @param timestamp 时间戳（精确到毫秒数）
     * @return 日期字符串
     */
    public static String formatTimeStamp2ChineseDateStr(Long timestamp) {
        return new SimpleDateFormat(CHINESE_DATE_FORMAT).format(new Date(timestamp));
    }

    /**
     * 将时间戳格式化为日期格式字符串:yyyy年M月d日
     *
     * @param timestamp 时间戳（精确到毫秒数））
     * @return 日期字符串
     */
    public static String formatTimeStamp2ChineseDateMDStr(Long timestamp) {
        return new SimpleDateFormat(CHINESE_DATE_FORMAT_M_D).format(new Date(timestamp));
    }

    /**
     * 将日期转换为时间戳
     *
     * @param date 日期对象
     * @return 时间戳数值（精确到毫秒）
     */
    public static long transDate2TimeStamp(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return 0;
    }

    /**
     * 将日期转换为unix时间戳
     *
     * @param date 日期对象
     * @return unix时间戳数值（精确到秒）
     */
    public static long transDate2UnixTimeStamp(Date date) {
        if (date != null) {
            return date.getTime() / COEFFICIENT;
        }
        return 0;
    }


    /**
     * unix时间戳转换
     *
     * @param unixTime
     * @return unix时间戳数值（精确到秒）
     */
    public static long transUnixTimeStamp2DateStamp(Long unixTime) {
        if (null != unixTime) {
            return unixTime * COEFFICIENT;
        }
        return 0;
    }

    /**
     * 将ISO格式的日期字符串转换为时间
     *
     * @param isoDateTime
     * @return
     */
    public static Date formatIsoDateTime2Date(String isoDateTime) {

        DateFormat[] customerDateFormats = new SimpleDateFormat[POSSIBLE_IOS_DATE_FORMATS.length];
        for (int i = 0; i < POSSIBLE_IOS_DATE_FORMATS.length; i++) {
            customerDateFormats[i] = new SimpleDateFormat(POSSIBLE_IOS_DATE_FORMATS[i], Locale.getDefault());
            customerDateFormats[i].setTimeZone(TIMEZONE);
        }

        int i = 0;
        while (i < customerDateFormats.length) {
            try {

                /*
                 * This Block needs to be synchronized, because the parse-Method in
                 * SimpleDateFormat is not Thread-Safe.
                 */
                synchronized (customerDateFormats[i]) {
                    Date date = customerDateFormats[i].parse(isoDateTime);
                    // UTC转成北京时间
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                    return calendar.getTime();
                }
            } catch (ParseException | NumberFormatException e) {
                i++;
            }
        }
        return null;
    }
}
