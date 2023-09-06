package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Override
    public PageInfo<Product> getProductPageList(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.getProductPageList(productDto);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Product product) {

        // 1.完成product的保存
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);

        // 2.完成productSku的保存
        //      从product中遍历出每一个sku对象对其赋值并保存
        //      sku其中我们输入的属性不需要输入，但我们通过选项选择的 固定属性 需要手动输入
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i); // 一个产品(spu)有多条sku对应不同的id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec()); // 产品的不同规格
            productSku.setProductId(product.getId());
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }

        // 3.完成productDetas的保存
        ProductDetails productDetails = new ProductDetails();
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetails.setProductId(product.getId());
        productDetailsMapper.save(productDetails);
    }

    @Override
    public Product getById(Long id) {
        // 查product
        Product product = productMapper.getById(id);
        // 查productSku
        List<ProductSku> productSkuList = productSkuMapper.getByProductId(id);
        product.setProductSkuList(productSkuList);
        // productDetails
        ProductDetails productDetails = productDetailsMapper.getByProductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());
        return product;
    }

    @Override
    public void updateById(Product product) {
        productMapper.updateById(product);

        product.getProductSkuList().forEach(productSku -> {
            productSkuMapper.updateById(productSku);
        });

        ProductDetails productDetails = productDetailsMapper.getByProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());

        productDetailsMapper.updateById(productDetails);

    }

    @Override
    public void deleteById(Long id) {
        // 逻辑删除产品
        productMapper.deleteById(id);
        productSkuMapper.deleteById(id);
        productDetailsMapper.deleteById(id);
    }

    @Override
    // 审批
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
            product.setId(id);
        if (auditStatus == 1){
            product.setAuditMessage("审核通过");
        }else if (auditStatus == -1){
            product.setAuditMessage("驳回");
        }
        product.setAuditStatus(auditStatus);
        productMapper.updateById(product);
    }

    @Override
    // 上架下架判断
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        productMapper.updateById(product);
    }


}
