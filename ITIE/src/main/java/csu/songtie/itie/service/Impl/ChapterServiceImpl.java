package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.songtie.itie.domain.entity.Chapter;
import csu.songtie.itie.mapper.ChapterMapper;
import csu.songtie.itie.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public List<Chapter> getChapterListById(String courseId) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_Id", courseId);
        return chapterMapper.selectList(queryWrapper);
    }
}
