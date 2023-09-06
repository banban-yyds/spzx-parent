package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    List<ProductSpec> getProductSpecPageList();

    void saveProductSpec(ProductSpec productSpec);

    void updateProductSpecById(ProductSpec productSpec);

    ProductSpec deleteProductSpecById(Long id);

    List<ProductSpec> findAll();
}
