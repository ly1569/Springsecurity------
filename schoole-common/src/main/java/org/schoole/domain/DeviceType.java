package org.schoole.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_device_type")
public class DeviceType {
    @TableId
    private Integer id ;
    private String deviceType ;
    private String deviceBrand ;
    private String photoSrc ;
    private String infoSrc ;
    private LocalDateTime createTime ;
    private LocalDateTime updateTime ;
}
