package com.daydao.dubbox.service.impl;

import com.daydao.dubbox.service.ICooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * CooService
 *
 * @author Chenpz
 * @package com.daydao.dubbox.service.impl
 * @date 2016/12/21/16:21
 */
@Service
public class CooService implements ICooService{
    private static final Logger LOGGER = LoggerFactory.getLogger(CooService.class);
    @Override
    public void coo(String cooName) {
        LOGGER.info("coo is called! input param:{}", cooName);
    }
}
