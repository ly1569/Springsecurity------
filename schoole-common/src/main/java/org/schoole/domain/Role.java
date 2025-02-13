package org.schoole.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色描述
     */
    private String roleKey;
    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */

    private Integer delFlag;
    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 评论
     */
    private String remark;


//    加上一个 角色对应的菜单 id 的属性
    @TableField(exist = false)
    private List<Integer> menuIdList ;
}
