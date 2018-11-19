package org.goskyer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginRequire {

    boolean strict() default true; //是否强制登录

    boolean virtual() default false; //是否是虚拟帐号

    boolean bind() default false; //是否允许第三方未绑定手机的校验

}
