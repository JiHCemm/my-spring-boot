package com.my_springboot.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rbac_admin")
@ApiModel(value="Admin对象", description="用户")
public class Admin implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updater;

    @ApiModelProperty(value = "是否删除 (0：未删除 1：已删除)")
    private Integer isDelete;


}
