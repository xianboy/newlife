package com.mdvns.mdvn.department.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    /*职位名称*/
    private String name;
    /*是否已删除*/
    @JsonIgnore
    private Integer isDeleted;
    /*创建人id*/
    private Long creatorId;
    /*创建时间*/
    private Timestamp createTime;

    public Position(String name) {
        this.name = name;
    }

}
