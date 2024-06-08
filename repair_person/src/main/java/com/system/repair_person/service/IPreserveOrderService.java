package com.system.repair_person.service;

import com.system.repair_person.model.PreserveOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.repair_person.vo.PreserveOrderDetailsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
public interface IPreserveOrderService extends IService<PreserveOrder> {

    boolean setEquipment(Integer id);

    PreserveOrderDetailsVO getPreserveOrderDetails(Integer preserveOrderId);
}
