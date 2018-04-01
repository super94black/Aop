package org.redrock.ioc.framework2.aop.core;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhang
 * @Date 2018/3/30 18:03
 * @Content 代理链
 */

/**
 * 因为横切逻辑可能不止有一种  比如获取用户信息的业务逻辑外 可能有日志记录 性能监控 权限管理等多种横切代码
 * 这些横切代码需要串成一串链子来顺序执行
 */
public class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] args;

    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex = 0;


    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] args, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.args = args;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }


    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object doProxyChain()throws Throwable{
        Object methodResult;
        if(proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else {
            methodResult = methodProxy.invokeSuper(targetObject,args);
        }
        return methodResult;
    }



}
