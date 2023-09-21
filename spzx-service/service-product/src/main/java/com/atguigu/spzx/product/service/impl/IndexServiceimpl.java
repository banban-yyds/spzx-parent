package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceimpl implements IndexService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;


    @Override
    public IndexVo getData() {
        List<Category> categoryList = categoryMapper.getFirstCategoryList();
        List<ProductSku> productSkuList = productSkuMapper.getProductSkuBySeleNum();
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return indexVo;
    }
}
