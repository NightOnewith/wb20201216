package com.ethan.ryds.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 自定义统一异常类
 * Created by ASUS on 2020/6/17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MyException extends RuntimeException {

    // 状态码
    private int code;
    // 异常信息
    private String msg;


    public MyException(String s) {
        this.msg = s;
    }
}
