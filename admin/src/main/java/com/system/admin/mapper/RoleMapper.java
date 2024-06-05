package com.system.admin.mapper;

import com.system.admin.model.Resource;
import com.system.admin.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Resource> listResources(Integer roleId);
}
