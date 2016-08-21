package com.zane.generic.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CustomClassPathContext extends FileSystemXmlApplicationContext {
@SuppressWarnings("unchecked")
 

public CustomClassPathContext(String s) throws BeansException {
super(s);
}

public CustomClassPathContext(String[] s) throws BeansException {
super(s);
}

protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
beanDefinitionReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
beanDefinitionReader.setNamespaceAware(true);
}
}
