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
    public static final int PARAM_ERROR = 600;
    public static final int USER_UNREGISTER = 1001;
    public static final int USER_REGISTERED = 1002;
    public static final int USER_DISABLE = 1003;
    public static final int USER_LOGIN_TIMEOUT = 1004;
    public static final int USER_PASSWORD_ERROR = 1005;
    public static final int USER_PERMISSION_DEFECT = 1006;
    public static final int FILE_FORMAT_ERROR = 2001;
    public static final int FILE_RESOLVE_ERROR = 2002;
    public static final int FILE_SHEET_NOT_UNIQUE = 2003;
    public static final int STRUCTURE_NOT_EXIST = 3001;
    public static final int STRUCTURE_HAS_EXISTED = 3002;
    public static final int STRUCTURE_NO_ANALYZE_ERROR = 3003;
    public static final int STRUCTURE_HAS_RELATED = 3004;
    public static final int MATERIAL_NO_BLANK = 4001;
    public static final int MATERIAL_LEVEL_BLANK = 4002;
    public static final int MATERIAL_AMOUNT_IS_NULL = 4003;
}
