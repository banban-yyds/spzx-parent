package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceimpl implements ProductSpecService {
    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> getProductSpecPageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ProductSpec> pageInfo = new PageInfo<>(productSpecMapper.getProductSpecPageList());
        return pageInfo;
    }

    @Override
    public void saveProductSpec(ProductSpec productSpec) {
        productSpecMapper.saveProductSpec(productSpec);
    }

    @Override
    public void updateProductSpecById(ProductSpec productSpec) {
        productSpecMapper.updateProductSpecById(productSpec);
    }

    @Override
    public ProductSpec deleteProductSpecById(Long id) {
        return productSpecMapper.deleteProductSpecById(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }


}
