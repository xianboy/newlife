package com.mdvns.mdvn.staff.domain;

import lombok.Data;

@Data
public class LoginRequest {
    /*账号*/
    private String account;
    /*密码*/
    private String password;

}
