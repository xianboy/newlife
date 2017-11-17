package com.mdvns.mdvn.common.util;

public class StringUtil {

    /**
     * 将字符串的首字母转换为大写
     * @param string
     * @return
     */
    public static String toUpperFristChar(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }
}
