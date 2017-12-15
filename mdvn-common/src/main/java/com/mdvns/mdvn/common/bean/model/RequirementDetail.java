package com.mdvns.mdvn.common.bean.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequirementDetail {

    private Long id;

    /*编号*/
    private String serialNo;

    /*状态*/
    private String status;

    /*进度*/
    private Double progress;

    /*概要*/
    private String summary;

    /*描述*/
    private String description;

    /*标签*/
    private List<TerseInfo> tags;

    /*优先级*/
    private Integer priority;

    /*过程方法*/
    private TerseInfo label;

    /*成员*/
    private List<RoleMember> members;

    /*开始结束时间*/
    private Long startDate, endDate;

    /*当前需求下所有story的story point 总和*/
    private Double storyPointAmount;

    /*附件*/



}
