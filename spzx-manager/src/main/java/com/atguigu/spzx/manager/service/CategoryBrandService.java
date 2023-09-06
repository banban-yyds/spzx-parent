package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto);

    void SaveCategoryBrand(CategoryBrand categoryBrand);

    void updateCategoryBrandById(CategoryBrand categoryBrand);

    CategoryBrand deleteCategoryBrandById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
