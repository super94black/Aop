package org.redrock.ioc.framework2.aop.test;

import org.redrock.ioc.framework2.aop.annotation.Aspect;
import org.redrock.ioc.framework2.aop.core.AspectTemplet;
import org.redrock.ioc.framework2.annotation.Component;
import org.redrock.ioc.framework2.annotation.Controller;

import java.lang.reflect.Method;

/**
 * @Author zhang
 * @Date 2018/3/31 16:24
 * @Content
 */
@Component
@Aspect(Controller.class)
public class LogAspect extends AspectTemplet {



    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        System.out.println("记录日志开始");
    }

    @Override
    public void after(Class<?> targetClass, Method method, Object[] args, Object result) throws Throwable {
        System.out.println("记录日志结束");
    }
}
