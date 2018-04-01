package org.redrock.ioc.framework2.aop.learn.dynamic_proxy;

import org.redrock.ioc.framework2.aop.learn.static_proxy.UserService;
import org.redrock.ioc.framework2.aop.learn.static_proxy.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author zhang
 * @Date 2018/3/30 16:15
 * @Content InvocationHandler 是代理实例的调用处理程序 实现的接口
 */
//这个相当于所有代理类的模板 通过这个类我们可以衍生出如  用户代理类 学生代理类 等等 省去了为每一个ServiceImpl写一个Proxy类
public class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }

    private void before(){
        System.out.println("取得用户信息之前，我先记录是谁在调用接口");
    }

     private void after(){
        System.out.println("取到用户信息之后，进行日志存储，记下来哪个用户的信息被调用了");
    }


    //做一个封装，可以在创建代理类的时候在main函数中少写点代码
    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    public static void main(String[] args) {
        UserService userServiceImpl = new UserServiceImpl();
        DynamicProxy proxyFactory = new DynamicProxy(userServiceImpl);

        //Proxy.newProxyInstance() 返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序。
        //这一步相当于为UserServiceImpl创建一个代理人 或者叫他代理类
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
                userServiceImpl.getClass().getInterfaces(),proxyFactory);

        //UserService userServiceProxy1 = proxyFactory.getProxy();

        System.out.println(userServiceProxy.getUserInfo());
    }
}
