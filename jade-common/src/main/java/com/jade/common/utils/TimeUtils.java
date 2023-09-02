/**
 * Project Name:MessagePlatform
 * File Name:TimeUtils.java
 * Package Name:com.ai.message.utils
 * Date:2017年9月7日下午3:15:53
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 */

package com.jade.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:TimeUtils <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2017年9月7日 下午3:15:53 <br/>  
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class TimeUtils {

    private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_DAY_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT_SQL = "yyyyMMddHHmmss";
    public static final String DATE_HOUR_FORMAT_SQL = "yyyyMMddHH";
    public static final String DATE_DAY_FORMAT_SQL = "yyyyMMdd";
    public static final String DATE_MONTH_FORMAT_SQL = "yyyyMM";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String LONG_TIME_FORMAT = "yyyyMMddHHmmssSSS";

    /*
     *      long year = Long.valueOf(date.substring(0, 4));
     *     long month = Long.valueOf(date.substring(4, 6));
     *     long day = Long.valueOf(date.substring(6, 8));
    */


    /**
     * @Title getDateByFormat
     * @Description 根据格式转换时间字符串为时间
     * @param timeString
     * @param format
     * @return
     * {@link }
     */
    public static Date getDateByFormat(String timeString, String format) {
        if (StringUtils.isEmpty(timeString) || StringUtils.isEmpty(format)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(timeString);
        } catch (ParseException e) {
            logger.error("转换时间失败，时间字符串：" + timeString, e);
        }

        return date;
    }

    /**
     * @Title getDateByFormat
     * @Description 根据格式转换时间
     * @param dateTime
     * @param format
     * @return
     * {@link }
     */
    public static Date getDateByFormat(Date dateTime, String format) {
        if (dateTime == null || format == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateTime);
        Date result = null;
        try {
            result = formatter.parse(dateString);
        } catch (ParseException e) {
            logger.error("getDateByFormat 转换时间失败", e);
        }
        return result;
    }

    /**
     * @Title getCurrentDayOfMonth
     * @Description 获取当前时间是几号
     * @return
     * {@link }
     */
    public static int getCurrentDayOfMonth() {
        //获取当前日期
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @Title getCurrentDayOfWeek
     * @Description 获取当前是周几
     * @return
     * {@link }
     */
    public static int getCurrentDayOfWeek() {
        //获取当前日期
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * @Title getCurrentDate
     * @Description 根据格式获取当前时间字符串
     * @param dateFormat
     * @return
     * {@link }
     */
    public static String getCurrentDateString(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    /**
     * @Title getDateString
     * @Description 获取特定格式的时间字符串
     * @param date
     * @param formmat
     * @return
     * {@link }
     */
    public static String getDateString(Date date, String formmat) {
        SimpleDateFormat format = new SimpleDateFormat(formmat);
        return format.format(date);
    }

    /**
     * @Title getCalendarFormmatTime
     * @Description 根据日期和格式化字符串，格式化时间格式  
     * @param calendar
     * @param formmatStr
     * @return
     * {@link }
     * @since 2017年9月27日 上午10:55:40
     */
    public static String getCalendarFormmatTime(Calendar calendar, String formmatStr) {
        //时间格式
        SimpleDateFormat format = new SimpleDateFormat(formmatStr);
        return format.format(calendar.getTime());
    }

    public static String getDateStringFormmat(String dateString, String srcFormmat, String tarFormmat) {
        Date date = getDateByFormat(dateString, srcFormmat);
        return getDateString(date, tarFormmat);
    }

}
  
