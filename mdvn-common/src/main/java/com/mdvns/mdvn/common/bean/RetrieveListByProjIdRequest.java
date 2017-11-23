package com.mdvns.mdvn.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RetrieveListByProjIdRequest {

    /*project的Id*/
    @NotNull(message = "projId不能为空")
    @Min(value = 1, message = "project id 不能小于1")
    private Long projId;

    /*用户Id*/
    @NotNull(message = "staffId不能为空")
    @Min(value = 1, message = "staffId 不能小于1")
    private Long staffId;

    private PageableCriteria pageableCriteria;
}
