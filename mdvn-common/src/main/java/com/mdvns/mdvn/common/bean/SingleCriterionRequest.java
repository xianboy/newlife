package com.mdvns.mdvn.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 查询某个对象的详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleCriterionRequest {
    /*当前用户Id*/
    @NotNull(message = "用户Id不能为空")
    @Min(value = 1, message = "用户Id不能小于1")
    private Long staffId;
    /*查询参数*/
    @NotNull(message = "查询参数不能为空")
    private String criterion;


}
