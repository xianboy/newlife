package com.mdvns.mdvn.template.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

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
public class Template {

    @Id
    @GeneratedValue
    private Long id;

    /*创建人ID*/
    @NotNull(message = "creatorId不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Column(nullable = false)
    private Long creatorId;

    /*名称*/
    @NotBlank(message = "name不能为空")
    @Column(nullable = false)
    private String name;

    /*编号*/
    private String serialNum;

    /*引用次数*/
    @Column(nullable = false)
    private Integer quoteCnt = MdvnConstant.ZERO;

    //模板行业类型
    @NotNull(message = "industryId不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Column(nullable = false)
    private Long industryId;

    /*创建时间*/
    @Column(columnDefinition = "timestamp", nullable = false)
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    //1~7的随机数
    @NotNull(message = "style不能为空")
    @Min(value = 1, message = "style的值不能小于1")
    @Column(nullable = false)
    private Integer style = MdvnConstant.ONE;

    /*是否已删除*/
    @JsonIgnore
    @Column(nullable = false)
    private Integer isDeleted = MdvnConstant.ZERO;

}
