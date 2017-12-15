package com.mdvns.mdvn.template.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Deliverable {

    @Id
    @GeneratedValue
    private Long id;

    /*创建人*/
    private Long creatorId;
    /*名称*/
    private String name;
    /*上一层对象id*/
    private Long hostId;

}
