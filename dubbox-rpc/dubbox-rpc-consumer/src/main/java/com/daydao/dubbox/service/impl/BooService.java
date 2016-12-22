package com.daydao.dubbox.service.impl;

import com.daydao.dubbox.bean.Foo;
import com.daydao.dubbox.service.IBooService;
import com.daydao.dubbox.service.ICooService;
import com.daydao.dubbox.service.IFooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BooService
 *
 * @author Chenpz
 * @package com.daydao.dubbox.service.impl
 * @date 2016/12/21/16:19
 */
@Service
public class BooService implements IBooService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooService.class);

    @Autowired
    private IFooService fooService;
    @Autowired
    private ICooService cooService;

    @Override
    public void boo(String booName) {
        LOGGER.info("boo is called! input param, booName:{}", booName);
        cooService.coo(booName);
        Foo foo = fooService.getFoo(booName);
        LOGGER.info("result foo:{}", foo.toString());
    }
}
