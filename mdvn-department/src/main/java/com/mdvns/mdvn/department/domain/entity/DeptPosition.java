package com.mdvns.mdvn.department.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class DeptPosition {

    private Long positionId;
    private Long deptId;
}
