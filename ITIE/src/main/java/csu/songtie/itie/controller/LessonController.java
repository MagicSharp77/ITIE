package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.entity.Lesson;
import csu.songtie.itie.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    LessonMapper lessonMapper;

    public CommonResponse<Lesson> getLessonsByCourseId (@PathVariable int courseId){
        return null;
    }
}
