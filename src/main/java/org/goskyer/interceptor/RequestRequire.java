package org.goskyer.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * @Description: 必填参数校验
 * @author: mia.he  
 * @date: 2017年8月11日 下午3:34:49
 * @version: V1.0  
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestRequire {
    
    /**
     * 请求当前接口所需要的参数,多个以小写的逗号隔开
     * @return
     */
    public String require() default "";
     
    /**
    *传递参数的对象类型
    */
    public Class<?> parameter() default Object.class;
}