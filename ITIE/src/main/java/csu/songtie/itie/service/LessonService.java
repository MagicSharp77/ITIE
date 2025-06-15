package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.Lesson;
import csu.songtie.itie.domain.vo.LessonListVO;

import java.util.List;

public interface LessonService {

    List<LessonListVO> getLessonListByCourseIdAndSortOrder(int courseId, int sortOrder);
}
