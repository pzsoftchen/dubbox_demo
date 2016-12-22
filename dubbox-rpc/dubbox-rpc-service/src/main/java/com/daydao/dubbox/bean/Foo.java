package com.daydao.dubbox.bean;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Foo
 *
 * @author Chenpz
 * @package com.daydao.dubbox.bean
 * @date 2016/12/21/15:38
 */
@Data
@Builder
public class Foo implements Serializable {

    private String fooName;
    private String barName;

}
