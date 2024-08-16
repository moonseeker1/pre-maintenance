package com.system.admin.service;

import com.system.admin.model.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.admin.model.Role;
import com.system.admin.param.LoginParam;
import com.system.admin.param.ModifyAdminParam;
import org.springframework.security.core.userdetails.UserDetails;

import javax.mail.MessagingException;
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


    String login(LoginParam loginParam);

    UserDetails loadUserByUsername(String username);

    boolean register(Admin admin);

    boolean updateRole(Integer adminId, List<Integer> roleIds);


    List<Role> getRoleList(Integer adminId);

    Boolean modifyById(Integer id, ModifyAdminParam param);

    boolean send(String email) throws MessagingException;
}
