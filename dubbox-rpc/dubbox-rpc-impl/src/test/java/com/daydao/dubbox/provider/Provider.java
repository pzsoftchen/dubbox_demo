package com.daydao.dubbox.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Provider
 *
 * @author Chenpz
 * @package com.daydao.dubbox.provider
 * @date 2016/12/21/16:36
 */
public class Provider {

    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider-applicationContext.xml");
        context.start();
        System.in.read(); // 按任意键退出
    }

}
