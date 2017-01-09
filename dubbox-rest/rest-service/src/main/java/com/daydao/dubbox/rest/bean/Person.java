package com.daydao.dubbox.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.bean
 * @date 2017/1/6/16:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long    personId    ;
    private String  personName  ;
    private String  sex         ;
    private Integer age         ;

}
