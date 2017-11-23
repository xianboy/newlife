package com.mdvns.mdvn.staff.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;



@Data
@NoArgsConstructor
public class CreateStaffRequest {
    /*添加Staff的人*/
    @NotBlank
    private Long creatorId;
    /*登录名*/
    @NotBlank
    private String account;
    /*姓名*/
    @NotBlank
    private String name;
    /*密码*/
    @NotBlank
    private String password;
    /*头像*/
    private String avatar;
    /*部门id*/
    private Long deptId;
    /*职位id*/
    private Long positionId;
    /*入职时间*/
    private Long hiredate;
    /*性别*/
    private String gender;
    /*职位级别*/
    private String positionLvl;
    /*邮箱*/
    private String email;
    /*手机号码*/
    private String mobile;
    /*标签*/
    private String tags;

}
