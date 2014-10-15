/**
 * 
 */
package com.esunny.satellite.report.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 */
public class CommonUtils {

    /**
     * 判断List是否为null或空。
     * 
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return null == list || list.isEmpty();
    }

    /**
     * 判断数组是否为null或空。
     * 
     * @param arr
     * @return
     */
    public static <T> boolean isEmpty(T[] arr) {
        return null == arr || arr.length == 0;
    }

    /**
     * 判断Map是否为null或空。
     * 
     * @param str
     * @return
     */
    public static <T, K> boolean isEmpty(Map<T, K> map) {
        return null == map || map.isEmpty();
    }

    /**
     * 判断List是否不为null或空。
     * 
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * 判断数组是否不为null或空。
     * 
     * @param arr
     * @return
     */
    public static <T> boolean isNotEmpty(T[] arr) {
        return !isEmpty(arr);
    }

    /**
     * 判断Map是否不为null或空。
     * 
     * @param map
     * @return
     */
    public static <T, K> boolean isNotEmpty(Map<T, K> map) {
        return !isEmpty(map);
    }

    public static Date stringToDate(String str) {
        Date date;
        if (StringUtils.isBlank(str)) {
            return new Date();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(str);
        } catch (ParseException e) {
            return new Date();
        }
        return date;
    }

    /**
     * 除空格
     */
    public static String delSpace(String str) {
        return str == null ? str : str.trim();
    }

    public static String delNull(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
