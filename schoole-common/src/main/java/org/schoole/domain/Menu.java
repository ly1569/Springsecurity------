package org.schoole.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单表(Menu)实体类
 *
 * @author makejava
 * @since 2021-11-24 15:30:08
 */
@TableName(value="sys_menu")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder //支持链式调用
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable {
    private static final long serialVersionUID = -54979041104113736L;

    @TableId
    private Integer id ;
//    private Long id ;
    private String redirect ;
    private String path ;
    private String component ;
    private String name ;
    private Integer parentId;
    private String perms ;
    private String icon ;
    private Integer createBy;
    private Date createTime;
    private Integer updateBy;
    private Date updateTime;
    private String isLeaf;
    private String title ;
    private Integer status ;
//    private boolean hidden ;
    @TableField(exist = false)
    private boolean hidden = false ;

    @TableField(exist = false)
    private List<Menu> children ;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,Object> meta ;
    public Map<String,Object> getMeta(){
        meta = new HashMap<>();
        meta.put("title",title);
        meta.put("icon",icon);
        return meta ;
    }



}
