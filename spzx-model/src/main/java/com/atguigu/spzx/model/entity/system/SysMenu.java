package com.atguigu.spzx.model.entity.system;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysMenu extends BaseEntity {
    private Long parentId;
    private String component;
    private String title;
    private Integer sortValue;
    private Integer status;

    // 存储下级列表
    private List<SysMenu> children;
}
