package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findByParentId(Long parentId);

    Integer findCountByParentId(Long id);

    List<Category> findAll();

    void batchAdd(@Param(value = "cachedDataList") List<CategoryExcelVo> cachedDataList);
}
