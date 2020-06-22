package com.ethan.ryds.common.exception;

import com.ethan.ryds.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 自定义统一异常类的处理器Handler
 * Created by ASUS on 2020/6/17.
 */
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(MyException.class)
    public R myExp(MyException myException) {

        return R.error(10001, myException.getMsg());
    }

    /**
     * 通用异常
     */
    @ExceptionHandler(Exception.class)
    public R aLLExp(Exception exception) {

        return R.error(500, exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {

        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e){

        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e){

        return R.error("没有权限，请联系管理员授权");
    }

}
