package com.my_springboot.util;

import java.util.Date;

/**
 * @author JiHC
 * @since 2020/8/13
 */
public class DateUtil {

    public static Date getCurrentTime() {
        return new Date (new Date ().getTime () + 3600l * 8000);//当前时间+8h
    }

}
