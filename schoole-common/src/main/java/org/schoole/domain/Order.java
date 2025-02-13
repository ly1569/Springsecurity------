package org.schoole.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_order")
public class Order {
    @TableId
    private Integer id ;
//    user_id, user_name, fix_type, fixman_id, fixman_name
    private Integer userId ;
    private String userName;
    private String fixType;
    private Integer fixmanId;
    private String fixmanName ;
}
