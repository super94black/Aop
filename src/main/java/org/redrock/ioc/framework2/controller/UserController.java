package org.redrock.ioc.framework2.controller;

import org.redrock.ioc.framework2.annotation.Controller;
import org.redrock.ioc.framework2.annotation.RequestMapping;
import org.redrock.ioc.framework2.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zhang
 * @Date 2018/4/1 11:37
 * @Content
 */
@Controller
public class UserController {

    @RequestMapping(value = "/user" ,method = RequestMethod.GET)
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response){
        System.out.println("获取用户的信息");
    }
}
