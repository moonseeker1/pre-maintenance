package com.system.repair_person.service.impl;

import com.system.repair_person.model.Fault;
import com.system.repair_person.mapper.FaultMapper;
import com.system.repair_person.service.IFaultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wugou
 * @since 2024-06-07
 */
@Service
public class FaultServiceImpl extends ServiceImpl<FaultMapper, Fault> implements IFaultService {

}
