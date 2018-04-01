package org.redrock.ioc.framework2.aop.core;

/**
 * @Author zhang
 * @Date 2018/3/30 18:02
 * @Content
 */
public interface Proxy {

    //执行链式代理
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
