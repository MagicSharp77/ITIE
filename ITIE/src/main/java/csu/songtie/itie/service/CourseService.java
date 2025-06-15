package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourseListByCategoryId(int categoryId);

    List<Course> getCourseListByTagId(int tagId);

    Course getSingleCourseDetail(int courseId);
}
