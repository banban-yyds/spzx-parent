package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BrandServiceimpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> getBrandPageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(brandMapper.getBrandPageList());
    }

    @Override
    public void saveBrand(Brand brand) {
        brandMapper.saveBrand(brand);

    }

    @Override
    public void updateBrandById(Brand brand) {
        brandMapper.updateBrandById(brand);
    }

    @Override
    public void deleteBrandById(Long id) {
        brandMapper.deleteBrandById(id);
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandMapper.getBrandById(id);
    }
}
