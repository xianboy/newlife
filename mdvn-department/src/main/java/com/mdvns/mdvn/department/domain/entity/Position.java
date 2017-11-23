package com.mdvns.mdvn.department.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue
    private Long id;
    /*部门id*/
    @Column(nullable = false)
    private Long deptId;
    /*职位名称*/
    private String name;
    /*是否已删除*/
    private Integer isDeleted;
    /*创建人id*/
    private Long creatorId;
    /*创建时间*/
    private Timestamp createTime;

    public Position(String name) {
        this.name = name;
    }

}
