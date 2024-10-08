package com.system.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.admin.bo.AdminUserDetails;
import com.system.admin.mapper.AdminMapper;
import com.system.admin.mapper.AdminRoleRelationMapper;
import com.system.admin.mapper.RoleMapper;
import com.system.admin.model.Admin;
import com.system.admin.model.AdminRoleRelation;
import com.system.admin.model.Resource;
import com.system.admin.model.Role;
import com.system.admin.param.LoginParam;
import com.system.admin.param.ModifyAdminParam;
import com.system.admin.service.IAdminService;
import com.system.admin.util.MailMsg;
import com.system.common.exception.Asserts;
import com.system.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MailMsg mailMsg;

    /**
     * 登录
     *
     * @param loginParam
     * @return
     */
    @Override
    public String login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPasswd();
        String email = loginParam.getEmail();
        String captcha = loginParam.getCaptcha();
        String token = null;
        Object object = redisTemplate.opsForValue().get(email);
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            if(!StringUtils.isNotBlank((String)redisTemplate.opsForValue().get(email))){
                Asserts.fail("未获取验证码");
            }
            if(!captcha.equals((String) redisTemplate.opsForValue().get(email))){
                Asserts.fail("验证码错误");
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
    public List<Role> getRoleList(Integer adminId){
        return adminMapper.getRoleList(adminId);
    }

    @Override
    public Boolean modifyById(Integer id, ModifyAdminParam param) {
        Admin admin=new Admin();
        admin.setId(id);
        admin.setUsername(param.getUsername());
        String password= param.getPasswd();
        if(!Objects.equals(password, "")){
            String encodePassword = passwordEncoder.encode(password);
            admin.setPasswd(encodePassword);
        }
        admin.setEmail(param.getEmail());
        admin.setName(param.getNickname());
        int rows =adminMapper.updateById(admin);
        return rows > 0;
    }

    @Override
    public boolean send(String email) throws MessagingException {
        String code = (String) redisTemplate.opsForValue().get(email);
        if((StringUtils.isNotBlank(code))){
            return false;
        }
        boolean b = mailMsg.mail(email);
        return b;
    }
}
