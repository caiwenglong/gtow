package com.yby.service.base.exception;

import com.yby.commonUtils.RS;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandle {

    // 捕获全部异常，exception代表所有的异常，一旦有异常，就会执行这个
    @ExceptionHandler(Exception.class)
    public RS error(Exception e) {
        e.printStackTrace();
        return RS.error();
    }



    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public RS error(CustomException e){
        e.printStackTrace();
        return RS.error().code(e.getExCode()).message(e.getExMassage());
    }
}
