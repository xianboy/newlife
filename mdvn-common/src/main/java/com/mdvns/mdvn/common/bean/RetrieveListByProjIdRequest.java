package com.mdvns.mdvn.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RetrieveListByProjIdRequest {

    /*project的Id*/
    @NotNull
    @Min(value = 1, message = "project id 不能小于1")
    private Long projId;

    private PageableCriteria pageableCriteria;
}
