package com.atguigu.spzx.model.entity.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private Long id;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

}