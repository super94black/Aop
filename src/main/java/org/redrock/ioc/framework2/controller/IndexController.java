package org.redrock.ioc.framework2.controller;

import org.redrock.ioc.framework2.annotation.Autowried;
import org.redrock.ioc.framework2.annotation.Controller;
import org.redrock.ioc.framework2.annotation.RequestMapping;
import org.redrock.ioc.framework2.annotation.RequestMethod;
import org.redrock.ioc.framework2.component.World;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    @Autowried
    World world;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("hello-world");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("world-test");
    }

    @RequestMapping(value = "/testone", method = RequestMethod.GET)
    public void testOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("world-test-one");
    }
}
