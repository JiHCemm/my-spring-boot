package com.my_springboot.constant;

/**
 * @author JiHC
 * @since 2020/8/22
 */
public enum ResultEnum {

    OK (200, "成功"),
    BAD_REQUEST (400, "错误请求"),
    NULL_PARAM (400, "参数空指针"),
    DATA_DELETE (400, "数据被删除"),
    NOT_FOUND (404, "未找到");

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
