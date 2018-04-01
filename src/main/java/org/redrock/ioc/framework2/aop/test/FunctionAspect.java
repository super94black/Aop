package org.redrock.ioc.framework2.aop.test;

import org.redrock.ioc.framework2.annotation.Controller;
import org.redrock.ioc.framework2.aop.annotation.Aspect;
import org.redrock.ioc.framework2.aop.core.AspectTemplet;

import java.lang.reflect.Method;

/**
 * @Author zhang
 * @Date 2018/4/1 11:32
 * @Content
 */
@Aspect(Controller.class)
public class FunctionAspect extends AspectTemplet{

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        System.out.println("性能监控开始");
    }

    @Override
    public void after(Class<?> targetClass, Method method, Object[] args, Object result) throws Throwable {
        System.out.println("性能监控结束");
    }
}
