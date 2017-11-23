package com.mdvns.mdvn.common.exception;

import lombok.*;

/**
 * 异常枚举类，定义异常信息
 * department:30000~30100
 * staff:30100~30200
 *
 */
public enum ErrorEnum {
     /*参数错误*/
    ILLEGAL_ARG("555"),
    /*更新请求和原数据相同, 请输入有效的更新数据*/
    THE_SAME_DATA("556"),
    /*数据不存在*/
    NOT_EXISTS("557"),
    /*数据已存在*/
    EXISTED("558")


    ;


    private String code;

//    private String msg;

    ErrorEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   /* public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }*/
}
