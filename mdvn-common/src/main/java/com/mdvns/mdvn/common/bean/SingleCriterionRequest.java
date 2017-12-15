package com.mdvns.mdvn.common.bean;

import com.mdvns.mdvn.common.bean.model.PageableCriteria;
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
    /*分页对象*/
    private PageableCriteria pageableCriteria;

    public SingleCriterionRequest(Long staffId, String criterion) {
        this.staffId = staffId;
        this.criterion = criterion;
    }
}
