package com.ppc.yygh.common.handler;

import com.ppc.yygh.common.exception.YyghException;
import com.ppc.yygh.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public R handleException(Exception exception){
        //exception.printStackTrace();//输出异常到日志
        log.error(exception.getMessage());
        return R.error().message(exception.getMessage());
    }

    @ExceptionHandler(value = {SQLException.class})
    public R handleSQLException(SQLException exception){
        //exception.printStackTrace();//输出异常到日志
        log.error(exception.getMessage());
        return R.error().message("sql异常");
    }

    @ExceptionHandler(value = {ArithmeticException.class})
    public R handleArithmeticException(ArithmeticException exception){
        //exception.printStackTrace();//输出异常到日志
        log.error(exception.getMessage());
        return R.error().message("数学异常");
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public R handleRuntimeException(RuntimeException exception){
        exception.printStackTrace();//输出异常到日志
        log.error(exception.getMessage());
        return R.error().message("编译异常");
    }

    @ExceptionHandler(value = {YyghException.class})
    public R handleYyghException(YyghException exception){
        exception.printStackTrace();//输出异常到日志
        log.error(exception.getMessage());
        return R.error().message(exception.getMessage()).code(exception.getCode());
    }
}
