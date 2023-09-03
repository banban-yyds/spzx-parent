package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/getBrandPageList/{pageNum}/{pageSize}")
    public Result getBrandPageList(@PathVariable(value = "pageNum")Integer pageNum,
                                   @PathVariable(value = "pageSize")Integer pageSize){
        PageInfo<Brand> pageInfo = brandService.getBrandPageList(pageNum,pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/saveBrand")
    public Result saveBrand(@RequestBody Brand brand){
        brandService.saveBrand(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateBrandById")
    public Result updateBrandById(@RequestBody Brand brand){
        brandService.updateBrandById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteBrandById/{id}")
    public Result deleteBrandById(@PathVariable(value = "id")Long id){
        brandService.deleteBrandById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getBrandById/{id}")
    public Result<Brand> getBrandById(@PathVariable(value = "id")Long id){
        Brand brand = brandService.getBrandById(id);
        return Result.build(brand, ResultCodeEnum.SUCCESS);
    }
}
