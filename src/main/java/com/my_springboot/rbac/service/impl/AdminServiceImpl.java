package com.my_springboot.rbac.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my_springboot.auth.TokenService;
import com.my_springboot.rbac.pojo.Admin;
import com.my_springboot.rbac.dao.AdminDAO;
import com.my_springboot.rbac.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my_springboot.user.pojo.UserInfoDO;
import com.my_springboot.util.MD5Util;
import com.my_springboot.util.Result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

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
        Admin admin = new Admin ();
        admin.setUserName (userName);
        admin.setPassword (password);
        List<Admin> admins = (List<Admin>) listByCondition (admin).getData ();
        if (null == admins||admins.size ()==0) {
            return Result.error (HttpStatus.NOT_FOUND.value (), HttpStatus.NOT_FOUND.getReasonPhrase ());
        }
        String token = tokenService.getToken (admins.get (0));
        return Result.success (token);
    }

    @Override
    public Result listByCondition(Admin admin) {
        if (null != admin) {
            String password = admin.getPassword ();
            if (null != password && !"".equals (password)) {
                admin.setPassword (MD5Util.MD5 (password));
            }
        }
        return Result.success (baseMapper.listByCondition (null, admin));
    }

    @Override
    public Result listAdminPage(Page<Admin> page) {
        page.setRecords (baseMapper.listByCondition (page, null));
        return Result.success (HttpStatus.FOUND.value (), HttpStatus.FOUND.getReasonPhrase (), page);
    }
}
