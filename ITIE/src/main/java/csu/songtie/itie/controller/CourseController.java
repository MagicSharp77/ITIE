package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.entity.Course;
import csu.songtie.itie.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/items")
public class CourseController {
    @Autowired
    private CourseService courseService;

    public CommonResponse<List<Course>> getCourseList (){
         List<Course> courses=courseService.getCourseList();
        return CommonResponse.createForSuccess(1,"<UNK>",courses);
    }

}
