package com.huajia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {

    /**
     * 路径
     */
    String path();

    /**
     * 高度占整个屏幕的百分比 - 默认50%
     */
    float heightPercent() default 0.5f;

    /**
     * 宽度占整个屏幕的百分比 - 默认50%
     */
    float widthPercent() default 0.5f;

}
