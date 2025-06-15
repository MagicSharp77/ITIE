package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import csu.songtie.itie.domain.entity.Category;
import csu.songtie.itie.domain.entity.Lesson;
import csu.songtie.itie.domain.vo.LessonListVO;
import csu.songtie.itie.mapper.CategoryMapper;
import csu.songtie.itie.mapper.LessonMapper;
import csu.songtie.itie.service.CategoryService;
import csu.songtie.itie.service.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public List<LessonListVO> getLessonListByCourseIdAndSortOrder(int courseId, int sortOrder) {
        LambdaQueryWrapper<Lesson> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Lesson::getCourseId, courseId)
                .eq(Lesson::getLessonSortOrder, sortOrder);
        List<Lesson> lessonList = lessonMapper.selectList(lambdaQueryWrapper);
        List<LessonListVO> lessonListVOS= new ArrayList<>();
        for (Lesson lesson : lessonList) {
            LessonListVO lessonListVO = new LessonListVO();
            BeanUtils.copyProperties(lesson,lessonListVO);
            lessonListVOS.add(lessonListVO);
        }
        return lessonListVOS;
    }
}
