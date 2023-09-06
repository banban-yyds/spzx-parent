package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductSpecService {
    PageInfo<ProductSpec> getProductSpecPageList(Integer pageNum, Integer pageSize);


    void saveProductSpec(ProductSpec productSpec);

    void updateProductSpecById(ProductSpec productSpec);

    ProductSpec deleteProductSpecById(Long id);

    List<ProductSpec> findAll();
}
