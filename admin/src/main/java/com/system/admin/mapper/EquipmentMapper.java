package com.system.admin.mapper;

import com.system.admin.model.Equipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wugou
 * @since 2024-06-07
 */
@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {

    List<Integer> selectIdByStatus(LocalDateTime now);
}
