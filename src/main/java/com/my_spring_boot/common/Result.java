package com.my_spring_boot.common;

import java.io.Serializable;

import com.my_spring_boot.constant.ResultEnum;
import lombok.Data;

@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -1802122468331526708L;

    private Integer code;// 状态码
    private String msg;// 返回信息
    private Object data;// 返回数据


    //自定义code,msg,data
    private Result(Integer code, String msg, Object data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    //自定义code,msg
    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //自定义data
    private Result(Object data) {
        this.data = data;
        this.code = ResultEnum.OK.getCode ();
        this.msg = ResultEnum.OK.getMsg ();
    }

    private Result() {
        this.code = ResultEnum.OK.getCode ();
        this.msg = ResultEnum.OK.getMsg ();
    }

    public static Result success(Integer code, String msg, Object data) {
        return new Result (code, msg, data);
    }

    public static Result success(Integer code, String msg) {
        return new Result (code, msg);
    }

    public static Result success(Object data) {
        return new Result (data);
    }

    public static Result error(ResultEnum re) {
        return new Result (re.getCode(), re.getMsg());
    }

    public static Result error(Integer code, String msg) {
        return new Result (code, msg);
    }

    public static Result error(Integer code, String msg, Object data) {
        return new Result (code, msg, data);
    }

    public static Result success() {
        return new Result ();
    }


}