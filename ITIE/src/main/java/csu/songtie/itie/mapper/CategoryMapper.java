package csu.songtie.itie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import csu.songtie.itie.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
