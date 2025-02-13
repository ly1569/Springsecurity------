package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.RoleMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    // 通过 roleId 查询 menuId 集合
    @Select("select sys_role_menu.menu_id from sys_role_menu,sys_menu where sys_role_menu.menu_id=sys_menu.id and sys_menu.is_leaf='Y' and sys_role_menu.role_id=#{roleId}")
    public List<Integer> getMenuIdListByRoleId(Integer roleId) ;
}
