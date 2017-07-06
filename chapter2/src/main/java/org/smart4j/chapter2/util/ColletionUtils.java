package org.smart4j.chapter2.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jing_xu on 2017/7/6.
 */
public final class ColletionUtils {
    /**
     * 判断Colletion是否为null
     */
    public static  boolean isEmpty(Collection<?> collection){
        if(collection!=null && !collection.isEmpty())
            return false;
        else
            return true;
    }
    public static  boolean isEmpty(Map<?,?> map){
        if(map!=null && !map.isEmpty())
            return false;
        else
            return true;
    }
}
