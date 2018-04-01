package org.redrock.ioc.framework2.aop.learn.static_proxy;

/**
 * @Author zhang
 * @Date 2018/3/30 15:54
 * @Content 利用代理模式 我们可以有更多的骚操作
 */
public class UserServiceProxy implements UserService{

    private UserService userService;

    public UserServiceProxy(){
        userService = new UserServiceImpl();
    }


    //这就相当于UserServiceImpl的一个代理人
    public String getUserInfo() {
        //代理人在执行UserServiceImpl之前先进行日志记录
        before();
        //然后在通知UserServiceImpl去执行获取用户信息的接口
        String info = userService.getUserInfo();
        after();
        //将拿到的用户信息返回回去
        return info;
    }

    private void before(){
        System.out.println("取得用户信息之前，我先记录是谁在调用接口");
    }

    private void after(){
        System.out.println("取到用户信息之后，进行日志存储，记下来哪个用户的信息被调用了");
    }

    public static void main(String[] args) {
        UserServiceProxy userServiceProxy = new UserServiceProxy();
        userServiceProxy.getUserInfo();
    }
}
