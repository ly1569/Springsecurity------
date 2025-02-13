package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.DeviceType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceMapper extends BaseMapper<DeviceType> {
    // 获取model
    @Select("select distinct model from sys_device_type")
    public List<String> getAllModel();

    // 获取model信息
    @Select("select * from sys_device_type where model=#{model}")
    List<DeviceType> getModelInfo(String model);

    // 通过model获取品牌
    @Select("select distinct device_brand from sys_device_type where model=#{model}")
    List<String> getBrandByModel(String model);
}
