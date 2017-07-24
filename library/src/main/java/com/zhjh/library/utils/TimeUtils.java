package com.zhjh.library.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String getDateFormat(String sourDate) {
        // 分析从 ParsePosition 给定的索引处开始的文本。如果分析成功，则将 ParsePosition 的索引更新为所用最后一个字符后面的索引
        String sourDateFormat = "yy-MM-dd HH-mm";
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourDateFormat);
        Date dateValue = simpleDateFormat.parse(sourDate, position);
        String returnString = simpleDateFormat.format(dateValue);
        return returnString;
    }


    public static String getNowDate() {
        // 分析从 ParsePosition 给定的索引处开始的文本。如果分析成功，则将 ParsePosition 的索引更新为所用最后一个字符后面的索引
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String returnString = dateFormat.format(date).toString();
        return returnString;
    }


    public static String getDate() {
        // 分析从 ParsePosition 给定的索引处开始的文本。如果分析成功，则将 ParsePosition 的索引更新为所用最后一个字符后面的索引
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String returnString = dateFormat.format(date).toString();
        return returnString;
    }

    public static String getDateFormatString(String sourDate) {
        // 分析从 ParsePosition 给定的索引处开始的文本。如果分析成功，则将 ParsePosition 的索引更新为所用最后一个字符后面的索引
//        String sourDateFormat = "yy-MM-dd HH-mm";
//        ParsePosition position = new ParsePosition(0);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourDateFormat);
//        Date dateValue = simpleDateFormat.parse(sourDate, position);
//        String returnString = simpleDateFormat.format(dateValue);
//        return returnString;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(sourDate, pos);

        SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd");
        String dateString = formatter1.format(strtodate);
        return dateString;
    }


    /**
     * 将秒转换成年月日格式
     *
     * @param time
     * @return 返回时间格式yyyy-MM-dd
     */
    public static String getStrTime(String time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = null;
        if (time.equals("")) {
            return "";
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // long loc_time = Long.valueOf(time);
        long loc_time = Long.parseLong(time);
        re_StrTime = sdf.format(new Date(loc_time * 1000L));
        return re_StrTime;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static long dateTimeToTimeStamp(String dayTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dayTime);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }

    public static int distanceDay(String day) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long to = 0;
        to = Calendar.getInstance().getTimeInMillis();
        long from = 0;
        try {
            from = df.parse(day).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) ((from - to) / (1000 * 60 * 60 * 24)) + 1;
    }




    public static String StringData(){

         String mYear;
         String mMonth;
         String mDay;
         String mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "星期"+mWay+": "+mYear + "." + mMonth + "." + mDay;
    }


    public static String StringData(String data){

        String mYear;
        String mMonth;
        String mDay;
        String mWay;
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "星期"+mWay+": "+mYear + "." + mMonth + "." + mDay;
    }

}
