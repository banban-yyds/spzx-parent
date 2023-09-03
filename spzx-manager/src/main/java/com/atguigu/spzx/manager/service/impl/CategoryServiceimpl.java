package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.manager.listener.CategortDataListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findByParentId(Long parentId) {
        // 1.查询所有的父分类
        List<Category> categoryList = categoryMapper.findByParentId(parentId);
        // 2.根据所有父分类的id 查询所有的父分类下是否还有子分类
        for (Category category : categoryList) {
            Long id = category.getId();
            Integer count = categoryMapper.findCountByParentId(id);
            if (count > 0){
                category.setHasChildren(true);
            }else {
                category.setHasChildren(false);
            }
        }
        return categoryList;
    }

    // EasyExcel 写(导出)
    @Override
    public void exportData(HttpServletResponse response) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            /**
             * 1.查询所有分类
             * 2.遍历并转换成Vo对象
             * */
            List<Category> categoryList = categoryMapper.findAll();
            List<CategoryExcelVo> list = new ArrayList<>();
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);
                list.add(categoryExcelVo);
            }

            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(list);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // EasyExcel 读(导入)
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, new CategortDataListener(categoryMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
