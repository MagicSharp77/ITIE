package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.Chapter;

import java.util.List;

public interface ChapterService {

    List<Chapter> getChapterListById(String courseId);
}
