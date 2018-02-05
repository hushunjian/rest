package com.m2m.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorMapping {
    @SuppressWarnings("rawtypes")
	private static Map<Class, KV> HOLDER = new HashMap<Class, KV>();

    static {
    }

    public static Integer getCode(Class<? extends SystemException> clazz) {
        return HOLDER.get(clazz).status;
    }

    public static String getMessage(Class<? extends SystemException> clazz) {
        return HOLDER.get(clazz).message;
    }

    private static class KV {
        public Integer status;
        public String message;

        @SuppressWarnings("unused")
		KV(Integer code, String message) {
            this.status = code;
            this.message = message;
        }
    }
}
