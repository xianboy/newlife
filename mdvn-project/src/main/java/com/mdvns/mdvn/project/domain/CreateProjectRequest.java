package com.mdvns.mdvn.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateProjectRequest {

    /*项目名称*/
    @NotBlank
    private String name;
    /*项目描述*/
    @NotBlank
    private String description;
    /*标签: 多个Id的字符串*/
    private String tags;
    /*项目优先级*/
    private Integer priority;
    /*负责人: 包含多个staff的id*/
    private String leaders;
    /*项目开始日期*/
    private Long startDate;
    /*项目结束日期*/
    private Long endDate;
    /*项目所选的模板: 多个模板的id*/
    private String templates;
    /*项目可调整系数:是否可用Integer*/
    private Double contingency;
    /*项目附件: 多个附件的id*/
    private String attaches;
    /*创建人Id*/
    @NotBlank(message = "创建人id不能为空")
    private String creatorId;



}
