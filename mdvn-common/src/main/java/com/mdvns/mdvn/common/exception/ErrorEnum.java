package com.mdvns.mdvn.common.exception;

import lombok.*;

@AllArgsConstructor
public enum ErrorEnum {
    ILLEGAL_Arg("555", "参数错误")

    ;


    private String code;

    private String msg;

    ErrorEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
