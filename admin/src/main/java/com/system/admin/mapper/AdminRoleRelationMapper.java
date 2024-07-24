package com.system.admin.mapper;

import com.system.admin.model.AdminRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.admin.model.Resource;
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
public interface AdminRoleRelationMapper extends BaseMapper<AdminRoleRelation> {

    List<Resource> getResourceList(Integer id);
}
