package com.mdvns.mdvn.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Requirement implements Serializable {


    /*id pk*/
    private Long id;

    /* 需求编号：Rxx */
    private String serialNum;

    /* model ID */
    private String modelId;

    /* requirement summary */
    private String summary;

    /* staff id of creator */
    private String creatorId;

    /* requirement description*/
    private String description;

    /* requirement priority*/
    private Integer priority;

    /*过程方法模块*/
    private String functionLabelId;

    /* start date of this requirement*/
    private Long startDate;

    /* end date of this requirement*/
    private Long endDate;

    /* create time of this requirement*/
    private Long createTime;

    /* requirement status, eg. New, Open, In progess, Closed .etc*/
    private String status;

    /* requirement RAG status, ie. Red, Amber, Green*/
    private String ragStatus;

    /* requirement progress*/
    private Double progress;

    /* total amount of story point and CR story point*/
    private Integer totalStoryPoint;

    /*是否被删除*/
    private Integer isDeleted;

    /*最后更新时间*/
    private Long lastUpdateTime;

    /*所属项目id*/
    private Long projId;

}
