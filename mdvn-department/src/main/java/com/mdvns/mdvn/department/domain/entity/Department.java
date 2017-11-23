package com.mdvns.mdvn.department.domain.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    /*部门编号*/
    private String serialNum;
    /*部门名称*/
    @Column(name = "name")
    private String name;
    /*是否已删除*/
    private Integer isDeleted;
    /*创建时间*/
    private Timestamp createTime;
    /*创建人id*/
    private Long creatorId;
    /*职位名称*/
    private String positions;

}
