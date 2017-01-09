package com.daydao.dubbox.rest.util;

import com.daydao.dubbox.rest.resp.RespData;
import com.daydao.dubbox.rest.resp.RetBean;

/**
 * RepUtil
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.util
 * @date 2017/1/6/16:36
 */
public final class RespUtils{


    public static <T> RespData createRespData(T resp){
        return new RespData(resp);
    }

    public static  RetBean createErrorRespData(String errorMsg){
        return new RetBean(1, errorMsg);
    }

    public static RetBean createErrorRespDataWithCode(int retCode, String errorMsg){
        return new RetBean(retCode, errorMsg);
    }

    public static RetBean createSuccRespDataWithMsg(String desc){
        return new RetBean(0, desc);
    }

    public static RetBean createSuccRespData(){
        return createSuccRespDataWithMsg("ok");
    }


}
