package org.redrock.ioc.framework2.aop.learn.static_proxy;

import org.redrock.ioc.framework2.aop.learn.static_proxy.UserService;

/**
 * @Author zhang
 * @Date 2018/3/30 15:52
 * @Content 正常开发流程下的用户实现类
 */
public class UserServiceImpl implements UserService {

    public String getUserInfo() {

        String info = "张三 男 XXXX";
        System.out.println("我获取到了用户的信息，准备返回给前端");
        return info;
    }
}
