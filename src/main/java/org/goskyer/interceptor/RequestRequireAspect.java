package org.goskyer.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.goskyer.common.MsgCode;
import org.goskyer.exception.CommonException;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * @Description: 必填参数校验-面向切面服务
 * @notice: 只适应用字符串参数的校验
 * @author: mia.he
 * @date: 2017年11月27日 下午3:40:26
 * @version: V1.0
 */
@Aspect
@Component
public class RequestRequireAspect {
    private static final Logger log = Logger.getLogger(RequestRequireAspect.class);


    public RequestRequireAspect() {
        log.info("初始化接口参数非空判断切面类...");
    }

    @Pointcut("@annotation(org.goskyer.annotation.RequestRequire)")
    public void controllerInteceptor() {
    }

    @Around("controllerInteceptor()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        //获取注解的方法参数列表
        Object[] args = pjp.getArgs();

        //获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();
        //获取方法上的注解
        RequestRequire require = method.getAnnotation(RequestRequire.class);

        //以防万一，将中文的逗号替换成英文的逗号
        String fieldNames = require.require().replace("，", ",");

        //从参数列表中获取参数对象
        Object parameter = null;
        for (Object pa : args) {
            //class相等表示是同一个对象
            if (pa.getClass() == require.parameter()) {
                parameter = pa;
            }
        }

        //通过反射去和指定的属性值判断是否非空
        Class cl = Objects.requireNonNull(parameter).getClass();
        for (String fieldName : fieldNames.split(",")) {

            //根据属性名获取属性对象
            Field f = cl.getDeclaredField(fieldName);

            //设置可读写权限
            f.setAccessible(true);

            String value = (String) f.get(parameter);

            //非空判断
            if (!StringUtils.isNotBlank(value)) {
                throw new CommonException(MsgCode.BAD_PARAM.getCode(), "参数：【" + fieldName + "】不允许为空");
            }
        }

        //如果没有报错，放行
        return pjp.proceed();
    }
}
