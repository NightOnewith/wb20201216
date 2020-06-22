package com.ethan.ryds.common.log.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RSysLog {

	String value() default "";
}
