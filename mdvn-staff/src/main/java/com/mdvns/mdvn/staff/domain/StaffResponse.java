package com.mdvns.mdvn.staff.domain;

import com.mdvns.mdvn.common.bean.DepartmentDetail;
import com.mdvns.mdvn.common.bean.Position;
import com.mdvns.mdvn.staff.domain.entity.Staff;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffResponse {
    /*员工信息*/
    private Staff staff;
    /*职位信息*/
    private Position position;
    /*部门*/
    private DepartmentDetail deptDetail;
}
