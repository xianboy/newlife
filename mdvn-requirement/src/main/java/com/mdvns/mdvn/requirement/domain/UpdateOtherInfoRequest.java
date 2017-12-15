package com.mdvns.mdvn.requirement.domain;

import com.mdvns.mdvn.common.bean.MemberRequest;
import com.mdvns.mdvn.common.bean.model.AddOrRemoveById;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateOtherInfoRequest {

    /*当前用户id*/
    @NotNull(message = "staffId不能为空")
    @Min(value = 1, message = "staffId不能小于1")
    private Long staffId;
    /*当前对象id*/
    @NotNull(message = "对象id不能为空")
    @Min(value = 1, message = "hostId不能小于1")
    private Long requirementId;
    /*新增或删除标签*/
    private AddOrRemoveById tags;
    /*优先级*/
    private Integer priority;
    /*新增或删除角色成员*/
    private List<MemberRequest> roleMembers;
    /*开始时间*/
    private Long startDate;
    /*结束时间*/
    private Long endDate;

}
