package com.daydao.dubbox.rest.resp;

/**
 * RespData
 *
 * @author Chenpz
 * @package com.daydao.dubbox.rest.resp
 * @date 2017/1/6/16:41
 */
public class RespData<T> extends RetBean {

    private T data;

    public RespData(int ret_code, String ret_desc, T data) {
        super(ret_code, ret_desc);
        this.data = data;
    }

    public RespData(int ret_code, String ret_desc) {
        super(ret_code, ret_desc);
    }

    public RespData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
