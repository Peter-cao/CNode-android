package com.demo.cnode_android;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ck on 2016/10/20.
 */

public class Utils {
    public static String getDateDiff(String last_reply_at){
        String  result;
        try{
            long minute = 1000 * 60;
            long hour = minute * 60;
            long day = hour * 24;
            long halfamonth = day * 15;
            long month = day * 30;
            long year = day * 365;
            long now = new Date().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date last_reply_date = sdf.parse(last_reply_at);
            long diffValue = now - last_reply_date.getTime();
            if(diffValue < 0){
                //非法操作
                return   result = "数据出错";
            }
            long yearC = diffValue / year;
            long monthC = diffValue / month;
            long weekC = diffValue / (7 * day);
            long dayC = diffValue / day;
            long hourC = diffValue / hour;
            long minC = diffValue / minute;
            if(yearC >= 1){
                result = yearC + "年以前";
            }else if(monthC >= 1){
                result = (monthC) + "个月前";
            }else if(weekC >= 1){
                result = (weekC) + "星期前";
            }else if(dayC >= 1){
                result = (dayC) + "天前";
            }else if(hourC >= 1){
                result = (hourC) + "小时前";
            }else if(minC >= 5){
                result = (minC) + "分钟前";
            }else{
                result = "刚刚发表";
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result = "数据出错";
            return result;
        }
    }
    public static String  formatDate(String create_at){
        String  result = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat new_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date create_at_date = sdf.parse(create_at);
            result = "创建于:"+new_sdf.format(create_at_date);

        }catch (Exception e ){
            result = "数据出错";
        }finally {
            return result;
        }

    }
}
