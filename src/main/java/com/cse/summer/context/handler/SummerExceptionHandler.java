package com.cse.summer.context.handler;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.model.dto.Response;
import com.cse.summer.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 应用通用异常处理器
 *
 * @author 王振琦
 */
@RestControllerAdvice
public class SummerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(SummerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Response<Object> handleException(Exception e) {
        logger.error("-----SummerExceptionHandler-----", e);
        return new Response<>(StatusCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = SummerException.class)
    public Response<Object> handleSummerException(SummerException e) {
        logger.error("-----SummerExceptionHandler-----", e);
        return new Response<>(e.getStatusCode());
    }
}
