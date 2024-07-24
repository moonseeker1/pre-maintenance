package com.system.repair_person.service;

import com.system.repair_person.model.RepairOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.repair_person.vo.RepairOrderDetailsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
public interface IRepairOrderService extends IService<RepairOrder> {

    boolean setEquipment(Integer id);

    RepairOrderDetailsVO getRepairOrderDetails(Integer repairOrderId);
}
