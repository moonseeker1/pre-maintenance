package com.system.admin.service;

import com.system.admin.model.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
public interface IResourceService extends IService<Resource> {
    public void insertResource(Resource resource);

    List<Resource> listAll();
}
