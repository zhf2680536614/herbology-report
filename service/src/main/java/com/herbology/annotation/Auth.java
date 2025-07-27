package com.herbology.annotation;

import java.lang.annotation.*;

/**
 * @author Roxy2222
 * @date 2025/7/27
 * @description
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	
	/**
	 * 权限名称, 对应数据库中authority表的name字段, 默认使用endpoint, 可指定
	 */
	String value() default ""; //
	
	/**
	 * 0-启用 1-禁用
	 */
	int status() default 0;
	
	/**
	 * 是否需要权限认证,  true 需要, false 不需要, 会自动转换成数据库中0需要, 1不需要
	 */
	boolean requireAuth() default true;
	
	String description() default "";
}
