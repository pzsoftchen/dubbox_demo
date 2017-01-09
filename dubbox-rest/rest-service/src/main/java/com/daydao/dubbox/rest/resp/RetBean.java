package com.daydao.dubbox.rest.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * MessageResp
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.resp
 * @date 2017/1/6/16:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetBean implements Serializable {
    private int     ret_code = 0;
    private String  ret_desc = "ok";
}
