package com.system.admin.service;

import com.system.admin.model.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
public interface IAdminService extends IService<Admin> {
    public void insertAdmin(Admin admin);
}
