package com.mdvns.mdvn.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *职位信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionModel {

    /*职位id*/
    private Long id;
    /*部门id*/
    private String deptId;
    /*职位名称*/
    private String name;
    /*是否已删除*/
    private Integer isDeleted;
    /*创建时间*/
    private Long createTime;
}
