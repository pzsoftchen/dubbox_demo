package com.daydao.dubbox.rest.service;

import com.daydao.dubbox.rest.bean.Person;
import com.daydao.dubbox.rest.resp.RetBean;

/**
 * Created by Lenovo on 2017/1/6.
 */
public interface IPersonService {

    RetBean addPerson(Person person);
    RetBean delPerson(Long personId);
    RetBean delPerson(Long corpId, Long personId);
    RetBean updatePerson(Person person);
    RetBean getPerson(Long personId);

}
