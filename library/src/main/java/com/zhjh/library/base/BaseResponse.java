package com.zhjh.library.base;


/**
 * Created by zhang on 2016/3/22.
 */
public class BaseResponse extends BaseEvent {
    private Object data;

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

}
