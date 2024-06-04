package com.system.admin.service.impl;

import com.system.admin.model.Resource;
import com.system.admin.mapper.ResourceMapper;
import com.system.admin.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void insertResource(Resource resource) {
        resourceMapper.insert(resource);
    }
}
