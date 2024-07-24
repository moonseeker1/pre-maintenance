package com.system.repair_person.service;

import com.system.repair_person.model.RepairPerson;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyu
 * @since 2024-06-07
 */
public interface IRepairPersonService extends IService<RepairPerson> {



    String login(String username, String passwd);

    UserDetails loadUserByUsername(String username);

    RepairPerson getByUsername(String username);
}
