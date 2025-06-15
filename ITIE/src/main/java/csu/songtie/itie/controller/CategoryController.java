package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.Category;
import csu.songtie.itie.domain.entity.Course;
import csu.songtie.itie.domain.entity.Tag;
import csu.songtie.itie.service.CategoryService;
import csu.songtie.itie.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public CommonResponse<List<Category>> getCategoryList() {
        return CommonResponse.createForSuccess(ResponseCode.COURSE_LIST_FETCH_SUCCESS.getCode(), ResponseCode.COURSE_LIST_FETCH_SUCCESS.getDescription(), categoryService.getCategoryList());
    }
    @GetMapping("/{categoryId}/tags")
    public CommonResponse<List<Tag>> getTagListByCourseId(@PathVariable int categoryId) {
        return CommonResponse.createForSuccess(ResponseCode.TAG_LIST_FETCH_SUCCESS.getCode(), ResponseCode.TAG_LIST_FETCH_SUCCESS.getDescription(),tagService.getTagListByCourseId(categoryId));
    }

}
