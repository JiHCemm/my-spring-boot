package com.my_springboot.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色关联
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("rbac_admin_role")
@ApiModel(value="AdminRole对象", description="用户角色关联")
public class AdminRole implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

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
