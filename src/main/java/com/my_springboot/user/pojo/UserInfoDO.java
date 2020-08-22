package com.my_springboot.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author JiHC
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_info")
@ApiModel(value="UserInfoDO对象", description="")
public class UserInfoDO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "性别：0.未知 1.男 2.女 ")
    private Integer gender;

    @ApiModelProperty(value = "skey")
    private String skey;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "session_key")
    private String sessionKey;

    @ApiModelProperty(value = "用户手机号")
    private Integer phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "国")
    private String country;

    @ApiModelProperty(value = "头像访问地址")
    private String avatarUrl;

    @ApiModelProperty(value = "第一次登录时间")
    private Date firstVisitTime;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastVisitTime;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "是否删除 0 未删除 1 已删除")
    private Integer isDelete;


}
