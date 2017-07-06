package org.smart4j.chapter2.util;


import com.sun.org.apache.xpath.internal.operations.Bool;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by jing_xu on 2017/7/5.
 * 转型操作工具
 */
public final class CastUtil {
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        String strValue = castString(obj);
        if (!isEmpty(strValue)) {
            try {
                doubleValue = Double.parseDouble(strValue);
            } catch (NumberFormatException e) {
                doubleValue = defaultValue;
            }
        }
        return doubleValue;
    }

    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        String strValue = castString(obj);
        if (!isEmpty(strValue)) {
            try {
                longValue = Long.parseLong(strValue);
            } catch (NumberFormatException e) {
                longValue = defaultValue;
            }
        }
        return longValue;
    }

    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        String strValue = castString(obj);
        if (!isEmpty(strValue)) {
            try {
                booleanValue = Boolean.parseBoolean(strValue);
            } catch (NumberFormatException e) {
                booleanValue = defaultValue;
            }
        }
        return booleanValue;
    }

    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        String strValue = castString(obj);
        if (!isEmpty(strValue)) {
            try {
                intValue = Integer.parseInt(strValue);
            } catch (NumberFormatException e) {
                intValue = defaultValue;
            }
        }
        return intValue;
    }
}
