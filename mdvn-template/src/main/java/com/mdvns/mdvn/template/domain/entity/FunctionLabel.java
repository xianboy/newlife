package com.mdvns.mdvn.template.domain.entity;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class FunctionLabel {

    @Id
    @GeneratedValue
    private Long id;

    /*创建人ID*/
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Column(nullable = false)
    private Long creatorId;

    /*名称*/
    @NotBlank(message = "名称不能为空")
    @Column(nullable = false)
    private String name;

    /*编号*/
    @Column(nullable = false)
    private String serialNo;

    /*父级id*/
    @Min(value = 1, message = "id不能小于1")
    private Long parentId;

    /*子级id*/
    @Min(value = 1, message = "id不能小于1")
    private Long subitemId;

    /*创建时间*/
    @Column(nullable = false)
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    /*是否已删除*/
    private Integer isDeleted = MdvnConstant.ZERO;

    /*子模块id*/
    private String subLabels;

    /*子模块名称*/
    @Transient
    private List<String> subLabelNames;


}
