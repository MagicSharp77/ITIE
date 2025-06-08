package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.entity.Category;
import csu.songtie.itie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

//    public CommonResponse<List<Category>> getAllCategories() {
//        return CommonResponse.createForSuccess(1,"",categoryService.getAllCategories);
//    }

}
