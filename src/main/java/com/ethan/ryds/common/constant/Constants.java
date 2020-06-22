package com.ethan.ryds.common.constant;

/**
 * 常量类
 * Created by ASUS on 2020/5/30.
 */
public class Constants {

    /**
     * token过期时间 12小时
     */
    private final static int EXPIRE = (60 * 60 * 12) * 1000;

    /**
     * 超级管理员ID
     */
    public static final Long SUPER_ADMIN = 1L;

    /**
     * http请求状态码
     *
     *      1XX 表示消息        （100：账号密码错误，110：账号被锁定）
     *      2XX 表示成功        （200：请求成功）
     *      3XX 表示重定向      （301：永久重定向，302：临时重定向，304：自上次请求未修改的文件）
     *      4XX 表示客户端错误  （400：错误的请求，401：未授权token认证失败，403：请求被拒绝，404：资源缺失或接口不存在）
     *      5XX 表示服务端错误  （500：服务端的未知错误，502：网关错误，503：服务暂时无法使用）
     */
    public enum HttpStatus {

        USER_NAME_OR_PASSWORD_ERROR(100, "账号或密码不正确"),

        USER_NAME_CLOCKED(110, "账号已被锁定,请联系管理员"),

        INTERNAL_SERVER_SUCCESS(200, "success"),  // 请求成功

        USER_UNAUTHORIZED(401, "invalid token"), // 登录/认证失败

        INTERNAL_SERVER_ERROR(500, "未知异常，请联系管理员");  // 服务器错误

        private final int status;

        private final String msg;

        HttpStatus(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * token 过期时间
     */
    public enum Jwt {

        JWT_EXPIRE_TTL(EXPIRE),

        JWT_ERRCODE_FAIL(401),

        JWT_ERRCODE_EXPIRE(405);

        private final int value;

        Jwt(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Jwt 密钥
     */
    public enum JwtSecret {

        JWT_SECRET("ryds_jwt_token");

        private final String value;

        JwtSecret(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 菜单类型
     *
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 分页字段
     */
    public enum PageField {
        /**
         * 当前页码
         */
        PAGE("page"),
        /**
         * 每页显示记录数
         */
        LIMIT("limit"),
        /**
         * 排序字段
         */
        ORDER_FIELD("sidx"),
        /**
         * 排序方式
         */
        ORDER("order"),
        /**
         *  升序
         */
        ASC("asc");

        private String value;

        PageField(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
