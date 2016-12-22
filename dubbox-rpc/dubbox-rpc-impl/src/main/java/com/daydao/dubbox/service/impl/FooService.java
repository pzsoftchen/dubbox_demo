package com.daydao.dubbox.service.impl;

import com.daydao.dubbox.bean.Foo;
import com.daydao.dubbox.service.IFooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * FooService
 *
 * @author Chenpz
 * @package com.daydao.dubbox.service.impl
 * @date 2016/12/21/15:55
 */
@Service
public class FooService implements IFooService {

    private Logger LOGGER = LoggerFactory.getLogger(FooService.class);

    @Override
    public Foo getFoo(String fooName) {
        LOGGER.info("input params, fooName:{}", fooName);
        return Foo.builder()
                .fooName(fooName)
                .build();
    }

    @Override
    public Foo getFoo(String fooName, String barName) {
        LOGGER.info("input params, fooName:{}, barName:{}", fooName, barName);
        return Foo.builder()
                .fooName(fooName)
                .barName(barName)
                .build();
    }

    @Override
    public void insertFoo(Foo foo) {
        LOGGER.info("insertFoo foo:{}", foo.toString());
        LOGGER.info("inserFoo success!");
    }

    @Override
    public void updateFoo(Foo foo) {
        LOGGER.info("updateFoo foo:{}", foo.toString());
        LOGGER.info("updateFoo success!");
    }
}
