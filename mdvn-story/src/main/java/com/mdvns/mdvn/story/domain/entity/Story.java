package com.mdvns.mdvn.story.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Story {

    @Id
    @GeneratedValue
    /*id pk*/
    private Long id;

    /* staff id of creator */
    @Column(nullable = false)
    private Long creatorId;

    /*关联主体编号*/
    @Column(nullable = false)
    private String hostSerialNo;

    /*编号(serialNo)：Pxx-Rxx-Sxx */
    @NotBlank(message = "编号不能为空")
    @Column(nullable = false)
    private String serialNo;

    /*概要(summary)*/
    @Column(nullable = false)
    private String summary;

    /*描述(description)*/
    @Column(columnDefinition = "text", nullable = false)
    private String description;

    /*优先级(priority)*/
    private Integer priority = MdvnConstant.ZERO;


    /* start date of this requirement*/
    private Timestamp startDate;

    /* end date of this requirement*/
    private Timestamp endDate;

    /* create time of this requirement*/
    @Column(nullable = false)
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    /*用户故事点(story point)*/
    private Double storyPoint;

    /*status, eg. New, Open, In progress, Closed .etc*/
    private String status = MdvnConstant.NEW;

    /*RAG status, ie. Red, Amber, Green*/
    private String ragStatus = MdvnConstant.AMBER;

    /*(进度)progress*/
    private Double progress = Double.valueOf(MdvnConstant.ZERO);

    /*是否被删除*/
    @JsonIgnore
    private Integer isDeleted = MdvnConstant.ZERO;

    /*成员数量*/
    @Transient//非持久化字段
    private Integer memberAmount = MdvnConstant.ZERO;



}
