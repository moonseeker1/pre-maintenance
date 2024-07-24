package com.system.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.admin.model.Resource;
import com.system.admin.model.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
public interface IRoleService extends IService<Role> {

    boolean updateResource(Integer roleId, List<Integer> resourceIds);


    List<Resource> listResources(Integer roleId);
}
