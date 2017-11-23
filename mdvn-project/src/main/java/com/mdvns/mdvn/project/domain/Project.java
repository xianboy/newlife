package com.mdvns.mdvn.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="project")
@Data
@NoArgsConstructor
public class Project {
    //主键
    @Id
    @GeneratedValue
    private Long id;
    /*项目名称*/
    private String name;
    /*项目编号: Pxx*/
    private String serialNum;
    /*项目描述*/
    @Column(columnDefinition = "text", nullable = false)
    private String description;
    /*项目优先级*/
    private Integer priority;
    /*项目开始日期*/
    private Timestamp startDate;
    /*项目结束时期*/
    private Timestamp endDate;
    /*创建时间*/
    private Timestamp createTime;
    /*项目状态*/
    private String status;
    /*状态色值*/
    private String ragStatus;
    /*效率值*/
    private Double efficiency;
    /*项目进度*/
    private Double progress;
    /*项目可调整系数*/
    private Double contingency;
    /*项目成本*/
    private Double cost;
    /*项目需求变更成本*/
    private Double crCost;
    /*story总数*/
    private Integer storyQty;
    /*storyPoint总数*/
    private Float storyPointQty;
    /*crStory总数*/
    private Integer crStoryQty;
    /*crStoryPoint总数*/
    private Integer crStoryPointQty;
    /*需求变更占比*/
    private Double crRate;
    /*是否被删除*/
    private Integer isDeleted;
    /*项目创建者Id*/
    private String creatorId;
    /*项目标签*/
    private String tags;
    /*项目的负责人*/
    private String leaders;
    /*项目模型：id*/
    private String templates;
    /*项目附件：id*/
    private String attaches;

    public void setStartDate(Long startDate) {
    }

    public void setEndDate(Long endDate) {
    }
}
