package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.schoole.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT\n" +
            "            DISTINCT m.`perms`\n" +
            "        FROM\n" +
            "            sys_user_role ur\n" +
            "                LEFT JOIN `sys_role` r ON ur.`role_id` = r.`id`\n" +
            "                LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`\n" +
            "                LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id`\n" +
            "        WHERE\n" +
            "            user_id = #{userid}\n" +
            "          AND r.`status` = 0\n" +
            "          AND m.`status` = 0")
    List<String> menuMapperSelectPermsByUserId(Integer userid);

    @Select("select a.* from sys_menu a, sys_role_menu b, sys_user_role c " +
            "where a.id = b.menu_id and b.role_id = c.role_id " +
            "and c.user_id = #{userId, jdbcType=INTEGER} and a.parent_id = #{pid, jdbcType=INTEGER}")
    List<Menu> UserServiceImplGetMenuListByid(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
