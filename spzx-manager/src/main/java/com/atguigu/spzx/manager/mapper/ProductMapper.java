package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getProductPageList(ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
}
