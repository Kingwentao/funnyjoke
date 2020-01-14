package com.example.libnavnoattion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * author: created by wentaoKing
 * date: created in 2020-01-12
 * description:
 */

@Target(ElementType.TYPE)
public @interface FragmentDestination {

    String pageUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;

}
