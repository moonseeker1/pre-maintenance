package com.system.admin.service;

import com.system.admin.model.RepairOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.admin.param.AddRepairOrderParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-06
 */
public interface IRepairOrderService extends IService<RepairOrder> {

    boolean generateRepairOrder(AddRepairOrderParam addRepairOrderParam);
}
