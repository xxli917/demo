package com.nucarf.base.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ${yuwenming} on 2018/11/20.
 */
public class TimeUtils {
    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getAudioCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(System.currentTimeMillis());
    }

    // 返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    /**
     * 获取当前短日期 格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getNowDateShort() {
        return dateToString(new Date(), "yyyy-MM-dd");
    }
    /**
     * 获取当前短日期 格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getDateShort(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }
    /**
     * 获取当前短日期 格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getNowDateShort(String format) {
        return dateToString(new Date(), format);
    }

    // Date转换字符串
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date date, String outFormat) {
        return new SimpleDateFormat(outFormat).format(date);
    }

    public static String getTmLongToStr(long time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(time);
    }

    public static String getTmLongToStr(long time,String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }

    //毫秒转秒
    public static String long2String(long time) {

        //毫秒转秒
        int sec = (int) time / 1000;
        int min = sec / 60;    //分钟
        sec = sec % 60;        //秒
        if (min < 10) {    //分钟补0
            if (sec < 10) {    //秒补0
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //秒补0
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }
    }
    public static Date getDate(String time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
