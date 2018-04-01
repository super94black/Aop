package org.redrock.ioc.framework2.aop.core;


import org.redrock.ioc.framework2.aop.annotation.Aspect;
import org.redrock.ioc.framework2.core.ClassLoader;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author zhang
 * @Date 2018/3/31 15:02
 * @Content
 */
public class AopLoader {

    private Set<Class<?>> classSet;
    private Map<Class<?>,Object> targetProxyMap;

    public AopLoader(ClassLoader classLoader) {

        this.classSet = classLoader.getClassSet();

        //初始化代理类和目标类对应关系
        try {

            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);

            targetProxyMap = new HashMap<>();
            for (Map.Entry<Class<?>,List<Proxy>> targetEntry :targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetClass,proxyList);
                //这里就得到了目标类和代理列表的对应关系
                targetProxyMap.put(targetClass,proxy);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Map<Class<?>, Object> getTargetProxyMap() {
        return targetProxyMap;
    }







    /** 第一步
     * 获取代理类和目标类之间的映射关系
     * 这里的代理类指的应该是切面类，一个切面类可能对应多个目标类
     * 比如 一个日志切面类可能对应多个实现类（UserServiceImpl、StuServiceImpl。。。。。。）
     * @return
     * @throws Exception
     */
    private Map<Class<?>,Set<Class<?>>> createProxyMap()throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<>();

        //获取所有的以模板为超类的代理类
        Set<Class<?>> proxyClassSet = getClassSetBySuper(AspectTemplet.class);

        for (Class<?> proxyClass :proxyClassSet) {
            //如果这个代理类有@Aspect注解标识
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                //获取这个@Aspect注解
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                //根据Aspect注解里面
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 获取应用包名下某父类（或者接口）的所有子类（或实现类）
     * @param superClass
     * @return
     */
    public Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> subClassSet = new HashSet<>();
        for (Class<?> clazz : classSet) {
            if(superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)){
                subClassSet.add(clazz);
            }
        }
        return subClassSet;
    }

    /**
     * 获取所有目标类，即@Aspect注解的括号内里 value的值
     * @param aspect
     * @return
     * @throws Exception
     */
    private Set<Class<?>> createTargetClassSet(Aspect aspect)throws Exception{
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }


    /**
     * 获取包名下面带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> annoClassSet = new HashSet<>();
        for (Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(annotationClass)){
                annoClassSet.add(clazz);
            }
        }
        return annoClassSet;
    }






    /**
     * 获取目标类和代理对象列表之间的映射关系
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{

        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>,Set<Class<?>>> proxyEntry :proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass:targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }

        return targetMap;
    }





}
