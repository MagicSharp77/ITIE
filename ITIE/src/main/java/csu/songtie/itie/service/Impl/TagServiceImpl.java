package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.songtie.itie.domain.entity.Category;
import csu.songtie.itie.domain.entity.Tag;
import csu.songtie.itie.mapper.TagMapper;
import csu.songtie.itie.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagListByCourseId(int categoryId) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        return tagMapper.selectList(queryWrapper);
    }
}
