package com.mdvns.mdvn.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RetrieveListBySingleArgRequest implements Serializable{
    @NotBlank(message = "参数不能为空")
    private String singleArg;

    private PageableCriteria pageableCriteria;

}
