package com.atguigu.spzx.feign.product;

import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientimpl implements ProductFeignClient{
    @Override
    public ProductSku getProductSkuBySkuId(Long skuId) {
        return null;
    }
}
