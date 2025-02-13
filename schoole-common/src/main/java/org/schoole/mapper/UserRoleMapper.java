package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    // 通过用户名查询角色
    @Select("select name from sys_role  where id in (select role_id from sys_user_role where user_id in (select id from sys_user where user_name=#{username}))")
    String roleName(String username);
}
