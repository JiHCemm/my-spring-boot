package com.my_springboot.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user_info")
@ApiModel(value="UserInfoDO对象", description="")
public class UserInfoDO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String username;

    private String password;


}
