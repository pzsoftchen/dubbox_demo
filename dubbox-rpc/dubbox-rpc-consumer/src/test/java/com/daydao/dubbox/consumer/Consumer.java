package com.daydao.dubbox.consumer;

import com.daydao.dubbox.service.IBooService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Consumer
 *
 * @author Chenpz
 * @package com.daydao.dubbox.consumer
 * @date 2016/12/21/16:39
 */
public class Consumer {

    private ClassPathXmlApplicationContext context;
    @Before
    public void iniContext(){
        context = new ClassPathXmlApplicationContext("classpath:consumer-applicationContext.xml");
    }

    @Test
    public void booService(){
        IBooService booService = (IBooService)context.getBean("booService");
        booService.boo("hello");
    }

}
