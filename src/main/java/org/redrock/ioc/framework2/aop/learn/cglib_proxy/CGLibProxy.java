package org.redrock.ioc.framework2.aop.learn.cglib_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.redrock.ioc.framework2.aop.learn.static_proxy.UserService;
import org.redrock.ioc.framework2.aop.learn.static_proxy.UserServiceImpl;

import java.lang.reflect.Method;

/**
 * @Author zhang
 * @Date 2018/3/30 16:53
 * @Content
 */
public class CGLibProxy implements MethodInterceptor {

    //这个方法是创建需要被代理的目标的子类 这个子类就是我们的代理类
    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }


    //这里拦截了父类的所有方法
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        //这一步才是真正执行父类的方法
        Object result = proxy.invokeSuper(obj,args);
        after();
        return result;
    }


    private void before(){
        System.out.println("取得用户信息之前，我先记录是谁在调用接口");
    }

    private void after(){
        System.out.println("取到用户信息之后，进行日志存储，记下来哪个用户的信息被调用了");
    }

    public static void main(String[] args) {
        CGLibProxy cgLibProxy = new CGLibProxy();
        UserService userProxy = cgLibProxy.getProxy(UserServiceImpl.class);
        String info = userProxy.getUserInfo();
        System.out.println(info);
    }
}
