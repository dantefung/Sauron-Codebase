package com.dantefung.dp.proxy.sample03.framework;

public class BeanDefinition {

    private Class beanClass;

    private String beanClassName;

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
