package com.zhjh.library.base;

/**
 * 所有javabean的基类，包含返回的公共属性
 * <p/>
 * BaseEvent
 * <p/>
 * 2014-12-9 下午2:40:40
 *
 * @version 1.0.0
 */

public class BaseResult<T> {

    private static final long serialVersionUID = 1L;
    protected String message ;
    protected Boolean success;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
