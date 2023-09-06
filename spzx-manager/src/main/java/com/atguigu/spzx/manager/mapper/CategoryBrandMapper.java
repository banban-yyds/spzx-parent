package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {

    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    void SaveCategoryBrand(CategoryBrand categoryBrand);

    void updateCategoryBrandById(CategoryBrand categoryBrand);

    CategoryBrand deleteCategoryBrandById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
