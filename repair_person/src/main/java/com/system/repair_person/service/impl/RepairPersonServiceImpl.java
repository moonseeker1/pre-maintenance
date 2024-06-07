package com.system.repair_person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.repair_person.bo.RepairPersonUserDetails;
import com.system.repair_person.model.RepairPerson;
import com.system.repair_person.mapper.RepairPersonMapper;
import com.system.repair_person.service.IRepairPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
@Service
public class RepairPersonServiceImpl extends ServiceImpl<RepairPersonMapper, RepairPerson> implements IRepairPersonService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RepairPersonMapper repairPersonMapper;

    @Override
    public String login(String username, String passwd) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(passwd,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {

        }
        return token;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
       RepairPerson repairPerson = getByUsername(username);
        if(repairPerson!=null){
            return new RepairPersonUserDetails(repairPerson);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
    @Override
    public RepairPerson getByUsername(String username) {
        QueryWrapper<RepairPerson> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<RepairPerson> repairPersonList = repairPersonMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(repairPersonList)) {
            RepairPerson repairPerson = repairPersonList.get(0);
            return repairPerson;
        }
        return null;
    }

}
