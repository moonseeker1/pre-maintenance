package com.system.admin.service.impl;

import com.system.admin.model.Role;
import com.system.admin.mapper.RoleMapper;
import com.system.admin.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public void insertRole(Role role) {
        baseMapper.insert(role);
    }
}
