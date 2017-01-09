package com.daydao.dubbox.rest.service;

import org.junit.Test;

/**
 * MainTest
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.service
 * @date 2017/1/9/14:54
 */
public class PersonServiceTest {



    @Test
    public void addPerson() throws Exception{
        String url = "http://localhost:8081/person/addPerson";
        String jsonStr = "{\"personId\":10001,\"personName\":\"张三\",\"sex\":\"男\",\"age\":25}";
        System.out.println(HttpUtils.postJsonStr(url, jsonStr));
    }

    @Test
    public void delPerson() throws Exception{
        String url1 = "http://localhost:8081/person/delPerson/10001";
        String url2 = "http://localhost:8081/person/delPerson/26/10001";
        System.out.println(HttpUtils.get(url1));
        System.out.println(HttpUtils.get(url2));
    }

    @Test
    public void updatePerson() throws Exception{
        String url = "http://localhost:8081/person/updatePerson";
        String jsonStr = "{\"personId\":10001,\"personName\":\"张三\",\"sex\":\"男\",\"age\":25}";
        System.out.println(HttpUtils.postJsonStr(url, jsonStr));
    }

    @Test
    public void getPerson() throws Exception{
        String url1 = "http://localhost:8081/person/getPerson/10001";
        System.out.println(HttpUtils.get(url1));
    }



}
