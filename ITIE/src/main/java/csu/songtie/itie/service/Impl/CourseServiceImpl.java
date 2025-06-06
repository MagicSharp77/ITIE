package csu.songtie.itie.service.Impl;

import csu.songtie.itie.domain.entity.Course;
import csu.songtie.itie.mapper.CourseMapper;
import csu.songtie.itie.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList() {
        return courseMapper.selectList(null);
    }
}
