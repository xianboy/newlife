package com.mdvns.mdvn.staff.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames ={"name"})})
public class Staff {
    @Id
    @GeneratedValue
    private Long id;
    //员工编号
    private String serialNum;
    /*账号*/
    private String account;
    /*密码*/
    @JsonIgnore
    private String password;
    /*姓名*/
    private String name;
    /*头像*/
    @Column(columnDefinition = "text")
    private String avatar;
    /*部门id*/
    private Long deptId;
    /*职位id*/
    private Long positionId;
    /*入职时间*/
    private Timestamp hiredate;
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
    /*添加时间*/
    private Timestamp createTime;
    /*最后更新时间*/
    private Timestamp lastUpdateTime;
}
