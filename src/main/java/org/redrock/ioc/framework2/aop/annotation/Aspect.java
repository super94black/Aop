package org.redrock.ioc.framework2.aop.annotation;



import java.lang.annotation.*;

/**
 * @Author zhang
 * @Date 2018/3/30 17:59
 * @Content 切面注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
