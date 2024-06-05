package com.system.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.admin.mapper.RoleMapper;
import com.system.admin.mapper.RoleResourceRelationMapper;
import com.system.admin.model.Resource;
import com.system.admin.model.Role;
import com.system.admin.model.RoleResourceRelation;
import com.system.admin.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleResourceRelationMapper roleResourceRelationMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public boolean updateResource(Integer roleId, List<Integer> resourceIds) {
        if(resourceIds==null){
            return false;
        }
        QueryWrapper<RoleResourceRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleResourceRelationMapper.delete(queryWrapper);
        for(Integer resourceId : resourceIds){
            RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
            roleResourceRelation.setRoleId(roleId);
            roleResourceRelation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(roleResourceRelation);
        }
        return true;
    }

    @Override
    public List<Resource> listResources(Integer roleId) {
        return roleMapper.listResources(roleId);
    }
}
