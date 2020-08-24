package com.my_springboot.constant;

/**
 * @author JiHC
 * @since 2020/8/22
 */
public enum ResultEnum {

    OK (200, "OK"),
    NULL_PARAM (400, "Required parameter is not present"),
    DATA_DELETE (400, "The data has been deleted"),
    NOT_FOUND (404, "The data was not found");

    private final int code;
    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
