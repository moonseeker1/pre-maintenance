package com.system.admin.service;

import com.system.admin.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
public interface IRoleService extends IService<Role> {
    public void insertRole(Role role);
}
