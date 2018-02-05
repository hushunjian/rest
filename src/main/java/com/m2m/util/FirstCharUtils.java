package com.m2m.util;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class FirstCharUtils {

    /**
     * 返回拼音首字母;遇不可转拼音字符串,返回#
     * @param input 任意字符串
     * @return 拼音首字母或#
     */
    public static String getInitial(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        if (trimmed.length() < 1) {
            return "#";
        }
        try {
            String pinyin = PinyinHelper.getShortPinyin(trimmed);
            String upperCases = pinyin.toUpperCase();
            //判断是否是大写字母开头
            if (upperCases.matches("(^[A-Z])(.*)")) {
                return upperCases.substring(0, 1);
            } else {
                return "#";
            }
        } catch (PinyinException e) {
            //遇不可转拼音字符串,返回#
            return "#";
        }
    }
}
