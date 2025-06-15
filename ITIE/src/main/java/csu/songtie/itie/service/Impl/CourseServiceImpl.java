package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.songtie.itie.domain.entity.Course;
import csu.songtie.itie.mapper.ChapterMapper;
import csu.songtie.itie.mapper.CourseMapper;
import csu.songtie.itie.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public List<Course> getCourseListByCategoryId(int categoryId) {
        LambdaQueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>().lambda();
        courseQueryWrapper.eq(Course::getCategoryId,categoryId);
        return courseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public List<Course> getCourseListByTagId(int tagId) {
        LambdaQueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>().lambda();
        courseQueryWrapper.eq(Course::getTagId,tagId);
        return courseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public Course getSingleCourseDetail(int courseId) {
        LambdaQueryWrapper<Course> courseQueryWrapper = new QueryWrapper<Course>().lambda();
        courseQueryWrapper.eq(Course::getCourseId,courseId);
        return courseMapper.selectOne(courseQueryWrapper);
    }
}
