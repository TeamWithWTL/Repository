package com.jcwx.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringFactory implements BeanFactoryAware {  
    private static BeanFactory beanFactory;

	@SuppressWarnings("static-access")
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	/** 
     * 根据beanName名字取得bean 
     * @param beanName 
     * @return 
     */  
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
        if(null != beanFactory){
            return (T) beanFactory.getBean(beanName);
        }
        return null;
    }
} 