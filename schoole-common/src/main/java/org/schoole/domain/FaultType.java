package org.schoole.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_fault_type")
public class FaultType {
    @TableId private Integer id ;
    private String userName ;
    private String deviceType ;
    @NotBlank(message = "设备品牌不能为空")
    private String deviceBrand ;
    private String faultType ;
    private String serviceType ;
    private String customerFaultDesc ;
    private Integer urgent ;
    private LocalDateTime createTime ;
    private LocalDateTime updateTime ;
    private String fixMan ;
    private LocalDateTime receiveTime ;
    @TableField(exist = false)
    private List<String> faultReasons ;
}
