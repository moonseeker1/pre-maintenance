package com.system.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.admin.bo.AdminUserDetails;
import com.system.admin.mapper.AdminMapper;
import com.system.admin.mapper.AdminRoleRelationMapper;
import com.system.admin.mapper.RoleMapper;
import com.system.admin.model.*;
import com.system.admin.service.IAdminService;
import com.system.common.exception.Asserts;
import com.system.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper  roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;
    /**
     * 登录
     *
     * @param admin
     * @return
     */
    @Override
    public String login(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPasswd();
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            System.out.println(1);
        } catch (AuthenticationException e) {
            log.info("登录异常:{}", e.getMessage());
        }
        return token;

    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        Admin admin = getAdminByUsername(username);
        if (admin != null) {
            List<Resource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 获取用户资源列表
     * @param id
     * @return
     */
    private List<Resource> getResourceList(Integer id) {
        return adminRoleRelationMapper.getResourceList(id);
    }

    /**
     * 根据用户名获取后台管理员
     * @param username
     * @return
     */
    private Admin getAdminByUsername(String username) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("username",username);
        List<Admin> adminList = adminMapper.selectList(adminQueryWrapper);
        if(!adminList.isEmpty()){
            return adminList.get(0);
        }
        return null;
    }

    /**
     * 注册
     * @param admin
     * @return
     */
    @Override
    public boolean register(Admin admin) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("username",admin.getUsername());
        List<Admin> adminList = adminMapper.selectList(adminQueryWrapper);
        if(!adminList.isEmpty()){
            return false;
        }
        String encodePassword = passwordEncoder.encode(admin.getPasswd());
        admin.setPasswd(encodePassword);
        adminMapper.insert(admin);
        return true;
    }

    @Override
    public boolean updateRole(Integer adminId, List<Integer> roleIds) {
        if(roleIds==null){
            return false;
        }
        QueryWrapper<AdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id",adminId);
        adminRoleRelationMapper.delete(queryWrapper);
        for(Integer roleId :roleIds){
            AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
            adminRoleRelation.setAdminId(adminId);
            adminRoleRelation.setRoleId(roleId);
            adminRoleRelationMapper.insert(adminRoleRelation);
        }
        return true;
    }

    @Override
    public List<Role> getRoleList() {
        return adminMapper.getRoleList();
    }
}
