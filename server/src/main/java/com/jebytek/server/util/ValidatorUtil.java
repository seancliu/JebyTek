package com.jebytek.server.util;

import com.jebytek.server.exception.ValidatorException;
import org.springframework.util.StringUtils;

public class ValidatorUtil {

    /**
     * 空校验（null or ""）
     */
    public static void require(Object str, String fieldName) {
        if (StringUtils.isEmpty(str)) {
            throw new ValidatorException(fieldName + " can't be empty!");
        }
    }

    /**
     * 长度校验
     */
    public static void length(String str, String fieldName, int min, int max) {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        int length = 0;
        if (!StringUtils.isEmpty(str)) {
            length = str.length();
        }
        if (length < min || length > max) {
            throw new ValidatorException(fieldName + " must be " + min + "~" + max + " characters.");
        }
    }
}
