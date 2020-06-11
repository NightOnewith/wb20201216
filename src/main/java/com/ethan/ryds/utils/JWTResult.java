package com.ethan.ryds.utils;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * 返回结果对象
 *
 * Created by ASUS on 2020/3/19.
 */
@Data
public class JWTResult {

    /**
     * 返回码
     */
    private int code;

    /**
     * 是否成功，代表结果的状态
     */
    private boolean success;

    /**
     * 验证过程中payload中的数据
     */
    private Claims claims;
}
