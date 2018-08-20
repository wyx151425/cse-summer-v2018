package com.cse.summer.util;

/**
 * 响应状态码
 *
 * @author 王振琦
 */
public class StatusCode {
    public static final int SUCCESS = 200;
    public static final int STATUS_ERROR = 300;
    public static final int SYSTEM_ERROR = 500;
    public static final int PARAM_ERROR = 601;
    public static final int USER_UNREGISTER = 7000;
    public static final int USER_REGISTERED = 7001;
    public static final int USER_DISABLED = 7002;
    public static final int USER_LOGIN_PASSWORD_ERROR = 7003;
    public static final int USER_LOGIN_TIMEOUT = 7004;
    public static final int FILE_FORMAT_ERROR = 8001;
    public static final int FILE_RESOLVE_ERROR = 8002;
    public static final int STRUCTURE_EXIST = 9001;
    public static final int STRUCTURE_NO_ERROR = 9002;
    public static final int MATERIAL_NO_EXIST = 10001;
}
