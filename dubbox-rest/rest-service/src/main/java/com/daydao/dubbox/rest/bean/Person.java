package com.daydao.dubbox.rest.bean;

import lombok.Builder;
import lombok.Data;

/**
 * Person
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.bean
 * @date 2017/1/6/16:33
 */
@Data
@Builder
public class Person {

    private Long    personId    ;
    private String  personName  ;
    private String  sex         ;
    private int     age         ;

}
