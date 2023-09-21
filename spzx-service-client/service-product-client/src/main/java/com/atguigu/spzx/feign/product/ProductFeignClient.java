package com.atguigu.spzx.feign.product;


import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product",fallback = ProductFeignClientimpl.class)
public interface ProductFeignClient {
    @GetMapping("/api/product/getProductSkuBySkuId/{skuId}")
    public abstract ProductSku getProductSkuBySkuId(@PathVariable(value = "skuId")Long skuId);
}
