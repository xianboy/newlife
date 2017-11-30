package com.mdvns.mdvn.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagModel {
    /*标签id*/
    private Long id;
    /*标签编号*/
    private String serialNum;
    /*标签名称*/
    private String name;
    /*标签被引用的次数*/
    private Integer quoteCnt;
    /*创建人id*/
    private Long creatorId;
    /*標簽色值*/
    private String color;
    /*標簽創建時間*/
    private Long createTime;
    /*后加的字段, 1~7随机给一个数字*/
    private Integer tagStyle;
    /*是否已删除*/
    @JsonIgnore
    private Integer isDeleted;
}
