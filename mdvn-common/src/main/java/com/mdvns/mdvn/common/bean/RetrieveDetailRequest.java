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
public class RetrieveDetailRequest {
    /*目标对象的Id*/
    @NotNull(message = "Id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Long id;
    /*查询人Id*/
    @NotNull(message = "Id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Long staffId;

}
