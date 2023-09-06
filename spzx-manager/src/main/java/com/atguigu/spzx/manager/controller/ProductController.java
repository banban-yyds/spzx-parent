package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<Product>> getProductPageList(@PathVariable(value = "page")Integer page,
                                                        @PathVariable(value = "limit")Integer limit,
                                                        ProductDto productDto){
        PageInfo<Product> pageInfo = productService.getProductPageList(page,limit,productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable(value = "id")Long id){
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product){
        productService.updateById(product);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id")Long id){
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    // 审核产品
    public Result updateAuditStatus(@PathVariable(value = "id")Long id,
                                    @PathVariable(value = "auditStatus")Integer auditStatus){
        productService.updateAuditStatus(id,auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updateStatus/{id}/{status}")
    // 上架下架产品
    public Result updateStatus(@PathVariable(value = "id")Long id,
                                    @PathVariable(value = "status")Integer status){
        productService.updateStatus(id,status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
