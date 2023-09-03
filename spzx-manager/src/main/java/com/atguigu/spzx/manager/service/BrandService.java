package com.atguigu.spzx.manager.service;


import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

public interface BrandService {

    PageInfo<Brand> getBrandPageList(Integer pageNum, Integer pageSize);

    void saveBrand(Brand brand);

    void updateBrandById(Brand brand);

    void deleteBrandById(Long id);

    Brand getBrandById(Long id);
}
