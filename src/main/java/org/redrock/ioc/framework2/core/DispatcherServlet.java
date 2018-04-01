package org.redrock.ioc.framework2.core;

import org.redrock.ioc.framework2.aop.core.AopLoader;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet("/*")
public class DispatcherServlet extends GenericServlet{

    private Map<Class<?>, Object> controllers;
    private Map<String, Method> handlers;
    private Map<Class<?>,Object> proxyMap;

    @Override
    public void init() throws ServletException {
        ClassLoader classLoader = new ClassLoader();
        BeanFactory beanFactory = new BeanFactory(classLoader);
        AopLoader aopLoader = new AopLoader(classLoader);
        controllers = beanFactory.getControllers();
        handlers = beanFactory.getHandlers();
        proxyMap = aopLoader.getTargetProxyMap();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String handlerKey = req.getMethod() + ":" + req.getRequestURI();
        Method handler = handlers.get(handlerKey);
        Class controller = handler.getDeclaringClass();
        //Object controller = controllers.get(handler.getDeclaringClass());
        Object proxy = proxyMap.get(controller);

        if (controller != null) {
            try {

                //handler.invoke(controller, req, resp);
                handler.invoke(proxy,req,resp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IOException) {
                    IOException exception = (IOException) cause;
                    exception.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }


}
