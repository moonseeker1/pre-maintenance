package com.system.admin.service;

import com.system.admin.model.PreserveOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.admin.vo.PreserveOrderDetailsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wugou
 * @since 2024-06-07
 */
public interface IPreserveOrderService extends IService<PreserveOrder> {

    PreserveOrderDetailsVO getPreserveOrderDetails(Integer preserveOrderId);
}
