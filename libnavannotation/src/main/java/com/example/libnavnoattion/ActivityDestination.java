package com.example.libnavnoattion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * author: created by wentaoKing
 * date: created in 2020-01-12
 * description: activity类型页面信息
 */

@Target(ElementType.TYPE)
public @interface ActivityDestination {

    String pageUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;
}
