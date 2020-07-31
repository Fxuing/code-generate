package com.fxuing.codegenerate.utils;

/**
 * 简单字符串处理工具类
 *
 * @Author: Hou_fx
 * @Date: 2020.7.3 16:56
 * @Description:
 */
public class StringSimpleUtil {
    private static final String UNDERLINE = "_";

    /**
     * <p>
     * The underline goes to the hump
     * <p>
     * For example: phone_code -> phoneCode
     *
     * @param s
     * @Author: Hou_fx
     * @Date: 2020.7.3 16:57
     * @Description:
     */
    public static String underlineToHump(String s) {
        StringBuilder sBuilder = new StringBuilder();
        s = s.toLowerCase();
        if (s.contains(UNDERLINE)) {
            String[] sp = s.split(UNDERLINE);
            if (sp.length > 0) {
                sBuilder.append(sp[0]);
                for (int i = 0; i < sp.length; i++) {
                    if (i != 0) {
                        char[] c = sp[i].toCharArray();
                        c[0] = (char) ((int) sp[i].toCharArray()[0] - 32);
                        sBuilder.append(c);
                    }
                }
            }
        }else{
            sBuilder.append(s);
        }
        s = sBuilder.toString();
        if (s.contains(UNDERLINE)) {
            underlineToHump(s);
        }
        return s;
    }
}
