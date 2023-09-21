package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {

    List<ProductSku> getProductSkuBySeleNum();

    List<ProductSku> getPageList(ProductSkuDto productSkuDto);

    ProductSku getProductSkuByskuId(Long skuId);

    List<ProductSku> getProductSkuListByprodyctId(Long productId);

    ProductSku getProductSkuBySkuId(Long skuId);
}
