package com.cse.summer.context.exception;

/**
 * 应用通用异常
 *
 * @author 王振琦
 */
public class SummerException extends RuntimeException {
    private int statusCode;

    public SummerException(int statusCode) {
        this.statusCode = statusCode;
    }

    public SummerException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
