package com.daydao.dubbox.service;

import com.daydao.dubbox.bean.Foo;

/**
 * Created by Lenovo on 2016/12/21.
 */
public interface IFooService {
    Foo getFoo(String fooName);
    Foo getFoo(String fooName, String barName);
    void insertFoo(Foo foo);
    void updateFoo(Foo foo);
}
