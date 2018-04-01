package org.redrock.ioc.framework1.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//@WebServlet(value = "/*")
public class DispatcherServlet extends HttpServlet{

    Map<String, Object> urlControllerMaps = new HashMap<>();

    @Override
    public void init() throws ServletException {
        ControllerLoader loader = new ControllerLoader();
        urlControllerMaps = loader.load();

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uri = {"index", "index"};
        String[] reqUri = req.getRequestURI().substring(1).split("/");
        if (reqUri != null && reqUri.length > 0) {
            for (int i = 0; i < 2 && i < reqUri.length; i++) {
                uri[i] = reqUri[i];
            }
        }
        String controllerName = uri[0].toLowerCase();
        String methodName = uri[1].toLowerCase();
        Object controller = urlControllerMaps.get(controllerName) == null ? urlControllerMaps.get("index") : urlControllerMaps.get(controllerName);
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                try {
                    method.invoke(controller, req, resp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
