package com.mdvns.mdvn.common.bean.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleMember {

    /*模板角色id和name*/
    private TerseInfo tmplRole;

    /*成员*/
    private List<TerseInfo> members;

}
