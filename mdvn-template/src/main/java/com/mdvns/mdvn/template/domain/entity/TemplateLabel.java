package com.mdvns.mdvn.template.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class TemplateLabel {

    @Id
    @GeneratedValue
    private Long id;

    /*创建人ID*/
    @NotNull(message = "creatorId不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Column(nullable = false)
    private Long creatorId;

    /*模板id*/
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Column(nullable = false)
    private Long tmplId;

    /*过程方法id*/
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Column(nullable = false)
    private Long labelId;

    /*创建时间*/
    @Column(columnDefinition = "timestamp", nullable = false)
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    /*是否已删除*/
    @JsonIgnore
    @Column(nullable = false)
    private Integer isDeleted = MdvnConstant.ZERO;

}
