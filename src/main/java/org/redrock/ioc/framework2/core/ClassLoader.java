package org.redrock.ioc.framework2.core;

import org.redrock.ioc.framework2.annotation.Component;
import org.redrock.ioc.framework2.annotation.Controller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassLoader {
    private static final String packageName = "org.redrock.ioc.framework2";

    private Set<Class<?>> classSet;
    private Set<Class<?>> controllerSet;
    private Set<Class<?>> componentSet;

    public ClassLoader() {
        load();
    }

    private void load() {
        classSet = new HashSet();
        try {
            Enumeration<URL> resouces = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (resouces.hasMoreElements()) {
                URL resouce = resouces.nextElement();
                String protocol = resouce.getProtocol();
                if (protocol.equalsIgnoreCase("file")) {
                    String packagePath = resouce.getPath();
                    loadClass(classSet, packageName, packagePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadComponentSet();
        loadControllerSet();

    }

    public Set<Class<?>> getControllerSet() {
        return controllerSet;
    }

    public Set<Class<?>> getComponentSet() {
        return componentSet;
    }

    public Set<Class<?>> getClassSet() {
        return classSet;
    }

    private void loadClass(Set<Class<?>> classSet, String packageName, String packagePath) {
        File[] files = new File(packagePath).listFiles(pathname -> pathname.isDirectory() || (pathname.isFile() && pathname.getName().endsWith(".class")));
        if (files != null && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    if (packageName != null && !packageName.equals("")) {
                        fileName = packageName + "." + fileName.substring(0, fileName.lastIndexOf("."));
                    }
                    Class<?> clazz = getClass(fileName);
                    classSet.add(clazz);
                } else {
                    String subPackageName = fileName;
                    if (packageName != null && !packageName.equals("")) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    String subPackagePath = fileName;
                    if (packagePath != null && !packagePath.equals("")) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }
                    loadClass(classSet, subPackageName, subPackagePath);
                }
            }
        }
    }

    private void loadComponentSet() {
        componentSet = new HashSet<>();
        if (classSet != null) {
            for (Class<?> clazz : classSet) {
                if (clazz.getAnnotation(Component.class) != null) {
                    componentSet.add(clazz);
                }
            }
        }
    }

    private void loadControllerSet() {
        controllerSet = new HashSet<>();
        if (classSet != null) {
            for (Class<?> clazz : classSet) {
                if (clazz.getAnnotation(Controller.class) != null) {
                    controllerSet.add(clazz);
                }
            }
        }
    }

    private Class<?> getClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
