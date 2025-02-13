package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    // 查询零件表中最大id
    @Select("select max(id) from sys_item ")
    public Integer maxItemId() ;
}
