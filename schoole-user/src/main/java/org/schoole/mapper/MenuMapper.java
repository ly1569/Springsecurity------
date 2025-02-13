package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.schoole.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    // 通过userid查询权限
    List<String> selectPermsByUserId(Long userid);

    // 通过userid查询List<Menu> 集合
//    @Select("select a.* from sys_menu a,sys_role_menu b,sys_user_role c where a.id = b.menu_id and b.role_id = c.role_id and c.user_id = #{userId} and a.parent_id = #{pid}")
//    List<Menu> getMenuListByUserId(@Param() Integer userId , Integer pid);

    @Select("select a.* from sys_menu a, sys_role_menu b, sys_user_role c " +
            "where a.id = b.menu_id and b.role_id = c.role_id " +
            "and c.user_id = #{userId, jdbcType=INTEGER} and a.parent_id = #{pid, jdbcType=INTEGER}")
    List<Menu> getMenuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);

}
