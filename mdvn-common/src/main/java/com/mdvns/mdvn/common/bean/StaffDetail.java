package com.mdvns.mdvn.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffDetail {
    //id
    private Long id;
    //员工编号
    private String serialNum;
    /*账号*/
    private String account;
    /*密码*/
    private String password;
    /*姓名*/
    private String name;
    /*头像*/
    private String avatar;
    /*所属部门*/
    private Long dept;
    /*职位id*/
    private Long positionId;
    /*入职时间*/
    private Long hiredate;
    /*效率值*/
    private Double effective;
    /*贡献值*/
    private Double contribution;
    /*推荐度*/
    private Double recommendation;
    /*工作饱和度*/
    private Double workSaturation;
    /*添加人*/
    private Long creatorId;
    /*职位等级*/
    private String positionLvl;
    /*email*/
    private String email;
    /*手机号*/
    private String mobile;
    /*状态:active, inactive, unregistered*/
    private String status;
    /*性别*/
    private String gender;
    /*标签*/
    private String tags;
    /*添加时间*/
    private Long createTime;
}
