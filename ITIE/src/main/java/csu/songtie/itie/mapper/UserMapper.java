package csu.songtie.itie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import csu.songtie.itie.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT COUNT(*) FROM user WHERE phone = #{phone}")
    boolean existsByPhone(String phone);
    @Select("SELECT user_id FROM user WHERE phone = #{phone}")
    String selectUserIdByPhone(String phone);
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User selectByPhone(String phone);
    @Select("SELECT * FROM user WHERE userId = #{userId}")
    User selectByUserId(String userId);
}
