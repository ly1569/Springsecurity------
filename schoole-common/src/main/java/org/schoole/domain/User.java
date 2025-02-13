package org.schoole.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.schoole.validation.Password ;
import org.schoole.validation.groups.Login;
import org.schoole.validation.CheckPhone;
import org.schoole.validation.groups.AddUser;
import org.schoole.validation.groups.UpdateUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户表(User)实体类
 *
 * @author 三更
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
    * 主键
    */
    @TableId
    private Long id;
    /**
    * 用户名
    */
    // 添加用户时唯一
    @NotBlank(message = "用户名不能为空" , groups = {Login.class, AddUser.class, UpdateUser.class})
    @JsonProperty("username") // 映射参数名为 "username"
    private String userName;
    /**
    * 昵称
    */
    private String nickName;
    /**
    * 密码
    */
    @NotBlank(message = "登陆时密码不能为空",groups = {Login.class})
    @Password(message = "密码含有特殊字符" , groups = {AddUser.class})
    private String password;
    /**
    * 账号状态（0正常 1停用）
    */
    private String status;
    /**
    * 邮箱
    */
    @Email(message = "邮箱格式错误" , groups = {AddUser.class, UpdateUser.class})
    @NotBlank(message = "邮箱不能为空" , groups = {AddUser.class , UpdateUser.class})
    private String email;
    /**
    * 手机号
    */
    @CheckPhone(message = "手机号格式不正确" , groups = {AddUser.class , UpdateUser.class})
    @NotBlank(message = "手机号不能为空" , groups = {AddUser.class , UpdateUser.class})
    private String phonenumber;
    /**
    * 用户性别（0男，1女，2未知）
    */
    private String sex;
    /**
    * 头像
    */
    private String avatar;
    /**
    * 用户类型（0管理员，1普通用户）
    */
    private String userType;
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
    * 删除标志（0代表未删除，1代表已删除）
    */
    private Integer delFlag;
    // 用户验证码
    private String identifyCode ;

    // 可以解码的密码
    private String password1 ;

    // user类中新增roleList属性，方便写入用户角色表
    @TableField(exist = false)
//    @JsonProperty("menuIdList") // 映射参数名为 "menuIdList"
    private List<Integer> roleIdList;
}
