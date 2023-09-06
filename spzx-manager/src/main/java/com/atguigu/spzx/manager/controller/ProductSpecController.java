package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/getProductSpecPageList/{pageNum}/{pageSize}")
    public Result<PageInfo<ProductSpec>> getProductSpecPageList(@PathVariable(value = "pageNum")Integer pageNum,
                                                                @PathVariable(value = "pageSize")Integer pageSize){
        PageInfo<ProductSpec> pageInfo = productSpecService.getProductSpecPageList(pageNum,pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result saveProductSpec(@RequestBody ProductSpec productSpec){
        productSpecService.saveProductSpec(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateProductSpecById(@RequestBody ProductSpec productSpec){
        productSpecService.updateProductSpecById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteProductSpecById(@PathVariable(value = "id") Long id){
        ProductSpec productSpec = productSpecService.deleteProductSpecById(id);
        return Result.build(productSpec, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<ProductSpec> productSpecList = productSpecService.findAll();
        return Result.build(productSpecList, ResultCodeEnum.SUCCESS);
    }

}
