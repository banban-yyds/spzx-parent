package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;


    @Override
    public PageInfo findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.getPageList(productSkuDto);
        PageInfo<ProductSku> pageInfo = new PageInfo<>(productSkuList);
        return pageInfo;
    }

    @Override
    public ProductItemVo item(Long skuId) {
        ProductItemVo productItemVo = new ProductItemVo();
        // 商品sku信息
        ProductSku productSku = productSkuMapper.getProductSkuByskuId(skuId);
        // 商品信息
        Product product = productMapper.getProductById(productSku.getProductId());
        // 商品轮播图列表
        List<String> sliderUrlList = Arrays.asList(product.getSliderUrls().split(","));
        // 商品详情图片列表
        ProductDetails productDetails = productDetailsMapper.getdetailsImageUrlByproductId(productSku.getProductId());
        List<String> detailsImageUrlList = Arrays.asList(productDetails.getImageUrls().split(","));
        // 商品规格信息
        JSONArray specValueList = JSON.parseArray(product.getSpecValue());
        // 商品规格对应商品skuId信息
        HashMap<String, Object> skuSpecValueMap = new HashMap<>();
        List<ProductSku> productSkuList = productSkuMapper.getProductSkuListByprodyctId(productSku.getProductId());
        productSkuList.forEach(skuItem -> skuSpecValueMap.put(skuItem.getSkuSpec(), skuItem.getProductId()));

        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSliderUrlList(sliderUrlList);
        productItemVo.setDetailsImageUrlList(detailsImageUrlList);
        productItemVo.setSpecValueList(specValueList);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        return productItemVo;
    }

    @Override  // cart远程调用 获取productSku
    public ProductSku getProductSkuBySkuId(Long skuId) {
        ProductSku productSku =  productSkuMapper.getProductSkuBySkuId(skuId);
        return productSku;
    }
}
