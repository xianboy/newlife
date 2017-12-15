package com.mdvns.mdvn.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateBasicInfoRequest {

    /*当前用户Id*/
    @NotNull(message = "用户Id不能为空")
    @Min(value = 1, message = "用户Id不能小于1")
    private Long staffId;

    /*当前用户Id*/
    @NotNull(message = "更新对象Id不能为空")
    @Min(value = 1, message = "更新对象Id不能小于1")
    private Long hostId;

    /*第一部分属性*/
    private String firstPart;

    /*描述*/
    private String secondPart;

}
