package org.schoole.domain;

import org.schoole.validation.CheckPhone;
import org.schoole.validation.Password;
import org.schoole.validation.groups.Register;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userForRegister {

    @Email(message = "邮箱格式"  , groups = {Register.class})
    @NotBlank(message = "邮箱不能为空"  , groups = {Register.class})
    private String email1 ;
    @Password(message = "密码不能含有特殊字符" , groups = {Register.class})
    @NotBlank(message = "注册密码不能为空" , groups = {Register.class})
    private String password1;

    @CheckPhone(message = "注册手机号格式" , groups = {Register.class})
    @NotBlank(message = "注册手机号不能为空"  , groups = {Register.class})
    private String phone1 ;

    @NotBlank(message = "注册用户名不能为空" , groups = {Register.class})
    private String username1;
    private Integer status1 ;
}
