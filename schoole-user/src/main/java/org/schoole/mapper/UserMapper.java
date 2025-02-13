package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.schoole.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 通过用户名查询用户id
    @Select("select id from sys_user where user_name=#{userName}")
    int getUserIdsByUserName(String userName);

    // 查询用户表中的最大id数
    @Select("select max(id) from sys_user")
    int getUserMaxId() ;

    // 通过用户名，用户手机号，用户邮箱查询用户密码
//    @Select("select sys_user.password from sys_user where user_name=#{userName} and email=#{email} and phonenumber=#{phonenumber}")
//    String getUserPassword(String userName , String email , String phonenumber);
    @Select("SELECT sys_user.password FROM sys_user " +
            "WHERE user_name = #{userName, jdbcType=VARCHAR} " +
            "AND email = #{email, jdbcType=VARCHAR} " +
            "AND phonenumber = #{phonenumber, jdbcType=VARCHAR}")
    String getUserPassword(@Param("userName") String userName,
                           @Param("email") String email,
                           @Param("phonenumber") String phonenumber);



//    //通过用户名写入用户验证码
//    @Update("update sys_user set identify_code = #{identifyCode} where user_name = #{userName}")
//    void setUserIdentifyCode( String identifyCode ,String userName ) ;
//
//
//    // 通过完整信息获取用户密码
//    @Select("select sys_user.password from sys_user where user_name=#{userName} and phonenumber=#{phonenumber} and email=#{email} and identify_code=#{identifyCode}")
//    String getUserPassword2(String userName , String  phonenumber , String email , String identifyCode ) ;
//
//    // 获取用户可解密的密码
//    @Select("select sys_user.password1 from sys_user where user_name=#{userName} and phonenumber=#{phonenumber} and email=#{email} and identify_code=#{identifyCode}")
//    String getUserPassword3(String userName , String  phonenumber , String email , String identifyCode ) ;
//
//    // 通过用户id查询用户信息
//    @Select("select* from  sys_user where  user_name = #{userName}")
//    User getUserInfoIndex(String userName) ;
//
//    // 查询所有用户名方法
//    @Select("select user_name from sys_user")
//    List<String> getAllUserName();

    // 通过用户名写入用户验证码
    @Update("UPDATE sys_user SET identify_code = #{identifyCode} WHERE user_name = #{userName}")
    void setUserIdentifyCode(@Param("identifyCode") String identifyCode, @Param("userName") String userName);

    // 通过完整信息获取用户密码
    @Select("SELECT sys_user.password FROM sys_user WHERE user_name=#{userName} AND phonenumber=#{phonenumber} AND email=#{email} AND identify_code=#{identifyCode}")
    String getUserPassword2(@Param("userName") String userName, @Param("phonenumber") String phonenumber, @Param("email") String email, @Param("identifyCode") String identifyCode);

    // 获取用户可解密的密码
    @Select("SELECT sys_user.password1 FROM sys_user WHERE user_name=#{userName} AND phonenumber=#{phonenumber} AND email=#{email} AND identify_code=#{identifyCode}")
    String getUserPassword3(@Param("userName") String userName, @Param("phonenumber") String phonenumber, @Param("email") String email, @Param("identifyCode") String identifyCode);

    // 通过用户id查询用户信息
    @Select("SELECT * FROM sys_user WHERE user_name = #{userName}")
    User getUserInfoIndex(@Param("userName") String userName);

    // 查询所有用户名方法
    @Select("SELECT user_name FROM sys_user")
    List<String> getAllUserName();

}
