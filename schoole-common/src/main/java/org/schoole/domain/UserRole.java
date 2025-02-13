package org.schoole.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole {
    private Long userId;
    private Integer roleId;
}
