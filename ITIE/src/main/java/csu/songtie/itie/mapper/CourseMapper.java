package csu.songtie.itie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import csu.songtie.itie.domain.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
