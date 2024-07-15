package com.xlm.rpc.springbootstarter.bootstrap;

import com.xlm.rpc.proxy.ServiceProxyFactory;
import com.xlm.rpc.springbootstarter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @author xlm
 * 2024/7/15 下午7:54
 *
 * 这段代码：
 *  扫描所有的bean，获取每一个bean的所有属性，如果某个属性含有注解@RpcReference，那么就
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    /**
     * Bean 初始化后执行，注入服务
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
//    @Override
    public Object postProcessAfterInitialization2(Object bean, String beanName) throws BeansException {
        // 获取每个bean的所有属性
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            // 查看哪些属性有@RpcReference注解，就给他创建代理
            RpcReference reference = field.getAnnotation(RpcReference.class);
            if (reference != null) {
                // 获取这个被添加注解属性的类型，比如下面的例子
                // @RpcReference
                // private UserService userService;
                Class<?> interfaceClass = reference.interfaceClass();
                if (interfaceClass == void.class) {
                    //如果是默认的void，就赋值为变量类型，这个例子中为UserService
                    interfaceClass = field.getType();
                }
                field.setAccessible(true); // 让userService可以被修改
                Object proxy = ServiceProxyFactory.getProxy(interfaceClass);// 创建UserService的代理
                try {
                    field.set(bean, proxy); // 把proxy赋值给原来的bean
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // 遍历对象的所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                // 为属性生成代理对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    field.set(bean, proxyObject);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败", e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
