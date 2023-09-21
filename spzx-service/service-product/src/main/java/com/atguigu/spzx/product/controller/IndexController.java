package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.service.IndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "首页接口管理")
//@CrossOrigin
@RestController
@RequestMapping("/api/product/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result<IndexVo> getData(){
        IndexVo indexVo = indexService.getData();
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }



}
