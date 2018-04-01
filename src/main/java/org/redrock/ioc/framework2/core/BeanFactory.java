package org.redrock.ioc.framework2.core;


import org.redrock.ioc.framework2.annotation.Autowried;
import org.redrock.ioc.framework2.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private Map<Class<?>, Object> controllers;
    private Map<String, Method> handlers;
    private Map<Class<?>, Object> components;

    private ClassLoader classLoader;

    public BeanFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
        initComponents();
        initControllersAndHandlers();
    }

    public Map<Class<?>, Object> getControllers() {
        return controllers;
    }

    public Map<String, Method> getHandlers() {
        return handlers;
    }

    private void initComponents() {
        Set<Class<?>> componentSet = classLoader.getComponentSet();
        components = new HashMap<>();
        for (Class<?> clazz : componentSet) {
            try {
                Object object = clazz.newInstance();
                components.put(clazz, object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void initControllersAndHandlers() {
        Set<Class<?>> controllerSet = classLoader.getControllerSet();
        controllers = new HashMap<>();
        handlers = new HashMap<>();
        for (Class<?> clazz : controllerSet) {
            try {
                String baseUri = clazz.getAnnotation(RequestMapping.class) != null ? clazz.getAnnotation(RequestMapping.class).value() : "";
                Object controller = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getAnnotation(Autowried.class) != null) {
                        Class<?> fieldClazz = field.getType();
                        Object fieldValue = components.get(fieldClazz);
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(controller, fieldValue);
                    }
                }
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping != null) {
                        String requestUri = requestMapping.method().name() + ":" + baseUri + requestMapping.value();
                        handlers.put(requestUri, method);
                    }
                }
                controllers.put(clazz, controller);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
