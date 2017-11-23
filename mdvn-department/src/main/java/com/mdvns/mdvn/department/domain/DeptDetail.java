package com.mdvns.mdvn.department.domain;

import com.mdvns.mdvn.department.domain.entity.Department;
import com.mdvns.mdvn.department.domain.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptDetail {

    /*部门信息*/
    private Department dept;
    /*职位信息*/
    private List<Position> position;

    public DeptDetail(Department department) {
        this.dept = department;
    }
}
