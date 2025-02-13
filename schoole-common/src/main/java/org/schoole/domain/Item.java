package org.schoole.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_item")
public class Item {
    @TableId
    private Integer id ;
    private String deviceType ;
    private String deviceBrand ;
    private Integer status ;
    private Date createTime ;
    private Date updateTime ;
}
