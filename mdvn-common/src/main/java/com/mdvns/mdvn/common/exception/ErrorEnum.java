package com.mdvns.mdvn.common.exception;

import lombok.*;

/**
 * 异常枚举类，定义异常信息
 * system:555~1000
 * department:30000~30100
 * staff:30100~30200
 * tag:30200~30300
 * file:30300~30400
 * template:30400~30500
 * project:30500~30600
 * requirement:30600~30700
 * story:30700~30800
 * task:30800~30900
 */
public enum ErrorEnum {
     /*参数错误*/
    ILLEGAL_ARG("555"),
    /*更新请求和原数据相同, 请输入有效的更新数据*/
    THE_SAME_DATA("556"),
    /*数据不存在*/
    NOT_EXISTS("557"),
    /*数据已存在*/
    EXISTED("558"),
    /*获取id和name失败*/
    GET_BASE_INFO_FAILED("559"),


    //staff
    /*用户不存在*/
    STAFF_NOT_EXISTS("30100"),


    //tag
    /*标签不存在*/
    TAG_NOT_EXISTS("30200"),


    //template
    /*新建模板时, 子过程方法不能为空*/
    TEMPLATE_SYSTEM_ERROR("30500"),
    SUB_LABEL_IS_NULL("30400"),
    /*自定义过程方法失败*/
    CUSTOM_LABEL_FAILD("30401"),
    /*模板角色不存在*/
    TEMPLATE_ROLE_NOT_EXISTS("30402"),
    /*过程方法不存在*/
    FUNCTION_LABEL_NOT_EXISTS("30403"),

    //requirement
    /*需求对象不存在*/
    REQUIREMENT_NOT_EXISTS("30600"),

    //story
    /*story不存在*/
    STORY_NOT_EXISTS("30700"),

    //task
    /*task不存在*/
    TASK_NOT_EXISTS("30800")
    ;


    private String code;

//    private String msg;

    ErrorEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }



}
