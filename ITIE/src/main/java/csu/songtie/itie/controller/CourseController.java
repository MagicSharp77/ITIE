package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.Chapter;
import csu.songtie.itie.domain.entity.Course;
import csu.songtie.itie.domain.vo.LessonListVO;
import csu.songtie.itie.service.ChapterService;
import csu.songtie.itie.service.CourseService;
import csu.songtie.itie.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private LessonService lessonService;

    @GetMapping("tags/{tagId}/courses")
    public CommonResponse<List<Course>> getCourseListByTagId (@PathVariable int tagId){
        return CommonResponse.createForSuccess(ResponseCode.COURSE_LIST_FETCH_SUCCESS.getCode(),ResponseCode.COURSE_LIST_FETCH_SUCCESS.getDescription(),courseService.getCourseListByTagId(tagId));
    }


    @GetMapping("/courses/{courseId}")
    public CommonResponse<Course> getCourseById (@PathVariable int courseId){
        return CommonResponse.createForSuccess(ResponseCode.SINGLE_COURSE_DETAIL_FETCH_SUCCESS.getCode(), ResponseCode.SINGLE_COURSE_DETAIL_FETCH_SUCCESS.getDescription(),courseService.getSingleCourseDetail(courseId));
    }
    @GetMapping("/courses/{courseId}/chapters")
    public CommonResponse<List<Chapter>> getChapterListById(@PathVariable String courseId) {
        return CommonResponse.createForSuccess(ResponseCode.CHAPTER_LIST_FETCH_SUCCESS.getCode(), ResponseCode.CHAPTER_LIST_FETCH_SUCCESS.getDescription(), chapterService.getChapterListById(courseId));
    }
    //获取某一course下的某一chapter（用sortOrder标记）的课程列表
    @GetMapping("/courses/{courseId}/lessons/{sortOrder}")
    public CommonResponse<List<LessonListVO>> getLessonsByCourseIdAndSortOrder (@PathVariable int courseId, @PathVariable int sortOrder){
        return CommonResponse.createForSuccess(ResponseCode.LESSON_LIST_FETCH_SUCCESS.getCode(), ResponseCode.LESSON_LIST_FETCH_SUCCESS.getDescription(),lessonService.getLessonListByCourseIdAndSortOrder(courseId,sortOrder));
    }

}
