package com.system.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.admin.model.RepairOrder;
import com.system.admin.param.AddRepairOrderParam;
import com.system.admin.vo.RepairOrderDetailsVO;

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

    RepairOrderDetailsVO getRepairOrderDetails(Integer repairOrderId);
}
