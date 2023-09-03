package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> getBrandPageList();

    void saveBrand(Brand brand);

    void updateBrandById(Brand brand);

    void deleteBrandById(Long id);

    Brand getBrandById(Long id);
}
