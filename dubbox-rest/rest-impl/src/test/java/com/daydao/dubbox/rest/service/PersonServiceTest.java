package com.daydao.dubbox.rest.service;

import com.alibaba.fastjson.JSON;
import com.daydao.dubbox.rest.bean.Person;
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
        Person person = Person.builder()
                .personId(10001L)
                .personName("张三")
                .sex("男")
                .age(25)
                .build();
        System.out.println(HttpUtils.postJsonStr(url, JSON.toJSONString(person)));
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
        Person person = Person.builder()
                .personId(10002L)
                .personName("李四")
                .sex("男")
                .age(22)
                .build();
        System.out.println(HttpUtils.postJsonStr(url, JSON.toJSONString(person)));
    }

    @Test
    public void getPerson() throws Exception{
        String url1 = "http://localhost:8081/person/getPerson/10001";
        System.out.println(HttpUtils.get(url1));
    }



}
