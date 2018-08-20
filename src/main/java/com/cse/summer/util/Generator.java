package com.cse.summer.util;

import java.util.UUID;

/**
 * @author 王振琦
 */
public class Generator {

    public static String getObjectId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String getReadonlyPassword() {
        return UUID.randomUUID().toString();
    }
}
