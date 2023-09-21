package com.atguigu.spzx.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    @Cacheable(value = "category",key = "'all'")
    public List<Category> findCategoryTree() {
        List<Category> categoryList = categoryMapper.findAll();
        // 获取一级分类
        List<Category> firstCategoryList = categoryList.stream().filter(category -> category.getParentId().longValue() == 0).collect(Collectors.toList());
        // 判空
        if (!CollectionUtils.isEmpty(firstCategoryList)){
            // 获取二级分类
            firstCategoryList.forEach(firstCategory -> {
                List<Category> secondCategoryList = categoryList
                        .stream()
                        .filter(category ->
                                category.getParentId().longValue() == firstCategory.getId().longValue())
                        .collect(Collectors.toList());
                // 判空
                if (!CollectionUtils.isEmpty(secondCategoryList)){
                    // 将流中的二级分类设置进其父分类中
                    firstCategory.setChildren(secondCategoryList);
                    // 获取三级分类
                    secondCategoryList.forEach(secondCategory -> {
                        List<Category> thirdCategoryList = categoryList.stream().filter(category -> category.getParentId().longValue() == secondCategory.getId().longValue()).collect(Collectors.toList());
                        secondCategory.setChildren(thirdCategoryList);
                    });
                }
            });
        }
        return firstCategoryList;
    }
}
