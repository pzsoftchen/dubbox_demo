import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RestProvider
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.provider
 * @date 2017/1/9/9:40
 */
public class RestProvider {

    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider-applicationContext.xml");
        context.start();
        System.in.read(); // 按任意键退出
    }
}
