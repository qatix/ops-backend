package com.quasar.backend.common.utils;

/**
 * @author xionghui
 */
public class Result {
    public static final Integer OK = 0;
    public static final Integer ERROR = -1;

    private Integer code;
    private String errorMsg;
    private Object data;

    public Result() {
        this.code = OK;
        this.errorMsg = "ok";
    }

    public Result(Integer code, String errorMsg) {
        super();
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public Result(String errorMsg, Object data) {
        super();
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public Result(Integer code, String errorMsg, Object data) {
        super();
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
