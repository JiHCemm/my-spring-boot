package com.my_spring_boot.rbac.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_spring_boot.auth.TokenService;
import com.my_spring_boot.constant.ResultEnum;
import com.my_spring_boot.rbac.pojo.Admin;
import com.my_spring_boot.rbac.dao.AdminDAO;
import com.my_spring_boot.rbac.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my_spring_boot.util.MD5Utils;
import com.my_spring_boot.common.Result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author JiHC
 * @since 2020-08-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminDAO, Admin> implements IAdminService {
    @Autowired
    private TokenService tokenService;

    @Override
    public Result login(String userName, String password) {
        if (null == userName || "".equals (userName)) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'userName' is not present");
        }
        if (null == password || "".equals (password)) {
            return Result.error (ResultEnum.NULL_PARAM.getCode (),
                    "Required String parameter 'password' is not present");
        }
        Admin admin = new Admin ();
        admin.setUserName (userName);
        admin.setPassword (MD5Utils.MD5 (password));
        List<Admin> admins = baseMapper.listByCondition (null, admin);
        if (null == admins || admins.size () == 0) {
            return Result.error (ResultEnum.NOT_FOUND.getCode (), ResultEnum.NOT_FOUND.getMsg ());
        }
        String token = tokenService.getToken (admins.get (0));
        return Result.success (token);
    }

    @Override
    public Result listByCondition(Integer pageNum, Integer pageSize, Admin admin) {
        if (null != admin) {
            String password = admin.getPassword ();
            if (null != password && !"".equals (password)) {
                admin.setPassword (MD5Utils.MD5 (password));
            }
        }
        if (null == pageNum || null == pageSize) {// 列表
            return Result.success (baseMapper.listByCondition (null, admin));
        } else {// 分页
            Page<Admin> page = new Page<> (pageNum, pageSize);
            page.setRecords (baseMapper.listByCondition (page, admin));
            return Result.success (page);
        }
    }
}
