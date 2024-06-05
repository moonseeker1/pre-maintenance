package com.system.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.admin.model.Admin;
import com.system.admin.model.Role;
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
public interface AdminMapper extends BaseMapper<Admin> {

    List<Role> getRoleList();
}
