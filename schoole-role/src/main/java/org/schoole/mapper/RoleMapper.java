package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Lazy;

@Mapper
@Lazy
public interface RoleMapper extends BaseMapper<Role> {


    // 查询角色表最大 id 值
    @Select("select max(id) from sys_role")
    int getRoleMaxId() ;

}
