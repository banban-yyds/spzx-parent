package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryBrandServiceimpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(Integer pageNum,
                                              Integer pageSize,
                                              CategoryBrandDto categoryBrandDto) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(categoryBrandMapper.findByPage(categoryBrandDto));
        return pageInfo;
    }

    @Override
    public void SaveCategoryBrand(CategoryBrand categoryBrand) {
        categoryBrandMapper.SaveCategoryBrand(categoryBrand);
    }

    @Override
    public void updateCategoryBrandById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateCategoryBrandById(categoryBrand);
    }

    @Override
    public CategoryBrand deleteCategoryBrandById(Long id) {
        return categoryBrandMapper.deleteCategoryBrandById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
