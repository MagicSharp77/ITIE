package csu.songtie.itie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import csu.songtie.itie.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo selectByUserId(String userId);
}
