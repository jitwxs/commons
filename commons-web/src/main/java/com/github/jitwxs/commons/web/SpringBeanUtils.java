package com.github.jitwxs.commons.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jitwxs
 * @date 2020年05月16日 23:04
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if(SpringBeanUtils.CONTEXT == null) {
            SpringBeanUtils.CONTEXT = context;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return CONTEXT;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
