package com.system.admin.service;

import com.system.admin.model.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.admin.model.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
public interface IAdminService extends IService<Admin> {


    String login(Admin admin);

    UserDetails loadUserByUsername(String username);

    boolean register(Admin admin);

    boolean updateRole(Integer adminId, List<Integer> roleIds);

    List<Role> getRoleList();
}
